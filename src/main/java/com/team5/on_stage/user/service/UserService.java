package com.team5.on_stage.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.on_stage.global.config.redis.RedisService;
import com.team5.on_stage.global.config.redis.dto.SmsVerificationData;
import com.team5.on_stage.global.config.s3.S3Uploader;
import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.user.dto.UserSmsVerificationCheckDto;
import com.team5.on_stage.user.dto.UserProfileDto;
import com.team5.on_stage.user.dto.UserSendSmsDto;
import com.team5.on_stage.user.entity.*;
import com.team5.on_stage.user.repository.UserRepository;
import com.team5.on_stage.util.sms.SmsUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final SmsUtil smsUtil;
    private final RedisService redisService;


    public void checkNicknameDuplicated(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new GlobalException(ErrorCode.NICKNAME_DUPLICATED);
        }
    }

    public void updateUserNickname(String username,
                                   String nickname) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        if (nickname.equals(user.getNickname())) {
            throw new GlobalException(ErrorCode.NOT_MODIFIED);
        }

        checkNicknameDuplicated(nickname);
        user.setNickname(nickname);
        userRepository.save(user);
    }

    public void updateUserDescription(String username,
                                      String description) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        if (description.equals(user.getDescription())) {
            throw new GlobalException(ErrorCode.NOT_MODIFIED);
        }

        user.setDescription(description);

        userRepository.save(user);
    }


    public void deleteUser(String username) {

        if (userRepository.findByUsername(username) == null) {

            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        userRepository.softDeleteUserByUsername(username);
    }


    public UserProfileDto getUserProfile(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        return UserProfileDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .description(user.getDescription())
                .profileImage(user.getProfileImage())
                .verified(user.getVerified())
                .build();
    }


    public UserProfileDto updateUserProfileImage(String username, MultipartFile profileImage) throws IOException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        String imageUrl = s3Uploader.upload(profileImage, "profileImages/" + username);
        user.setProfileImage(imageUrl);
        userRepository.save(user);

        return getUserProfile(username);
    }


    public void setUserProfileDefault(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        user.setProfileImage("https://s3-on-stage.s3.ap-northeast-2.amazonaws.com/profileImages/defaultProfile.jpg");

        userRepository.save(user);
    }


    /* SMS 본인인증 */

    // Todo: 예외처리
    public SingleMessageSentResponse sendSmsToVerify(UserSendSmsDto userSendSmsDto) {

        String username = userSendSmsDto.getUsername();
        String phoneNumber = userSendSmsDto.getPhoneNumber().replaceAll("-",""); // 형식에 맞게 변환
        User foundUser = userRepository.findByUsername(username);

        if (foundUser == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        /* Redis에 저장하기 위해 가공 */

//      1. 인증코드 생성
        String verificationCode = generateVerificationCode();

//      2. key에 사용할 날짜정보 생성
        String requestTime = LocalDateTime.now().toString();

//      3. 인증 정보를 저장할 객체 생성
        SmsVerificationData data = SmsVerificationData.builder()
                .username(username)
                .verificationCode(verificationCode)
                .phoneNumber(phoneNumber)
                .requestTime(requestTime)
                .build();

//      /* Redis 저장 */

//      4. 객체 직렬화
        // Todo: 예외처리
        ObjectMapper objectMapper = new ObjectMapper();

        String verificationData;

        try {
            verificationData = objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new GlobalException(ErrorCode.VERIFY_REQUEST_ERROR);
        }

//      5. 직렬화 된 객체 저장
        redisService.setSmsVerificationData(username, verificationData, requestTime);

//      6. 인증 확인 시 사용할 요청 시간 정보를 전화번호화 함께 저장
        redisService.setSmsVerificationRequestTime(username, userSendSmsDto.getPhoneNumber(), requestTime);

//      7. 인증번호 전송
        return smsUtil.sendVerificationCode(phoneNumber, verificationCode);
    }


    // Todo: 현재는 인증 시 바로 변경되지만, 추후 변경 신청해서 관리자가 변경시키는 방식으로 바꾸기
    @Transactional
    public Boolean verifyUser(UserSmsVerificationCheckDto verificationCheckDto) {

//      1. 요청자의 User 객체 정보
        String username = verificationCheckDto.getUsername();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        /* Redis에 저장된 인증 정보 조회 */

//      2. 인증 요청 시간 조회 - 요청 시간 일치 여부도 동시에 확인
        String phoneNumber = verificationCheckDto.getPhoneNumber();
        String savedRequestTime = redisService.getSmsVerificationRequestTime(username, phoneNumber);

//      3. 인증 정보 조회
        String savedVerificationDataS = redisService.getVerificationData(username, savedRequestTime);

//      4. 역직렬화
        ObjectMapper objectMapper = new ObjectMapper();

        SmsVerificationData savedVerificationData;

        try {
            savedVerificationData = objectMapper.readValue(savedVerificationDataS, SmsVerificationData.class);

        } catch (JsonProcessingException e) {
            throw new GlobalException(ErrorCode.VERIFY_REQUEST_ERROR);
        }

//      5. 인증 정보 확인 - 전화번호
        String savedPhoneNumber = verificationCheckDto.getPhoneNumber();

        if (!phoneNumber.equals(savedPhoneNumber)) {
            throw new GlobalException(ErrorCode.PHONENUMBER_UNMATCHED);
        }

//      6. 인증 정보 확인 - 인증코드
        String verificationCode = verificationCheckDto.getVerificationCode();
        String savedCode = savedVerificationData.getVerificationCode();

        if (!verificationCode.equals(savedCode)) {
            throw new GlobalException(ErrorCode.VERIFY_CODE_UNMATCHED);
        }

        /* 인증 완료 후 처리 */

//      7. 인증 정보 삭제
        redisService.deleteVerificationData(username, savedRequestTime);
        redisService.deleteVerificationRequestTime(username, phoneNumber);

//      8. 변경
        user.setVerified(Verified.VERIFIED);

        userRepository.save(user);

        return true;
    }


    public String convertNicknameToUsername(String nickname) {

        User user = userRepository.findByNickname(nickname);

        return user.getUsername();
    }


    // Todo: 인증코드 중복 검증. 근데 username과 조합하는데 굳이 필요 있을까?
    private String generateVerificationCode() {

        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        String verificationCode;

        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int idx = (int) (charSet.length * Math.random());
            codeBuilder.append(charSet[idx]);
        }
        verificationCode = codeBuilder.toString();

        return verificationCode;
    }

}
