package com.team5.on_stage.user.controller;

import com.team5.on_stage.global.config.jwt.TokenUsername;
import com.team5.on_stage.subscribe.service.SubscribeService;
import com.team5.on_stage.user.dto.UserProfileDto;
import com.team5.on_stage.user.dto.UserSendSmsDto;
import com.team5.on_stage.user.dto.UserSmsVerificationCheckDto;
import com.team5.on_stage.user.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "User Controller", description = "유저 기능 컨트롤러")
@AllArgsConstructor
@RequestMapping("/user")
@RestController
public class UserApiController {

    private final UserService userService;
    private final SubscribeService subscribeService;


    @Operation(summary = "프로필 정보 수정 엔드포인트", description = "로그인한 사용자의 닉네임 혹은 소개글을 변경한다.")
    @PatchMapping
    public ResponseEntity<String> updateUserProfile(@TokenUsername String username,
                                                    @RequestParam String field,
                                                    @RequestParam String value) {

        if (field.equals("nickname")) {
            userService.updateUserNickname(username, value);
            return ResponseEntity.ok(field);
        }
        else if (field.equals("description")) {
            userService.updateUserDescription(username, value);
            return ResponseEntity.ok(field);
        }

        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }


    @Operation(summary = "프로필 사진 변경 엔드포인트", description = "로그인한 사용자의 프로필 사진을 업로드한 사진으로 변경한다.")
    @PatchMapping("/profile")
    public ResponseEntity<UserProfileDto> updateUserProfileImage(@TokenUsername String username,
                                                                 @RequestPart("profileImage")   MultipartFile profileImage) throws IOException {
        return ResponseEntity.ok(userService.updateUserProfileImage(username, profileImage));
    }


    @Operation(summary = "기본 프로필 사진으로 변경하는 엔드포인트", description = "로그인한 사용자의 프로필 사진을 기본 프로필 사진으로 변경한다.")
    @PatchMapping("/profile/default")
    public ResponseEntity<Void> updateUserProfileDefault(@TokenUsername String username) {

        userService.setUserProfileDefault(username);

        return ResponseEntity.ok().build();
    }


    // 좋아요 기능
    @Operation(summary = "다른 사용자의 링크를 즐겨찾기에 등록하는 엔드포인트", description = "마음에 드는 사용자의 링크를 로그인한 사용자의 즐겨찾기 목록에 저장한다.")
    @PostMapping("/subscribe/{username}")
    public ResponseEntity<Boolean> subscribeLink(@TokenUsername String subscriber,
                                                 @PathVariable("username") String subscribed) {

        return ResponseEntity.ok(subscribeService.subscribeLink(subscriber, subscribed));
    }


    // 본인 프로필 정보 조회
    @Operation(summary = "로그인한 사용자의 프로필 정보를 가져오는 엔드포인트", description = "로그인한 사용자의 마이페이지 및 링크 관리 화면에 보여질 프로필 정보를 조회한다.")
    @GetMapping
    public ResponseEntity<UserProfileDto> getMyProfile(@TokenUsername String username) {

        return ResponseEntity.ok(userService.getUserProfile(username));
    }


    // 타인 방문 시 프로필 조회
    @Operation(summary = "다른 사용자의 프로필 정보를 가져오는 엔드포인트", description = "다른 사용자의 링크에 방문했을 때, 해당 사용자의 프로필 정보를 조회한다.")
    @GetMapping("/{username}")
    public ResponseEntity<UserProfileDto> getOtherProfiles(@PathVariable String username) {

        return ResponseEntity.ok(userService.getUserProfile(username));
    }


    // nickname -> username 변환
    @Hidden
    @GetMapping("/convert/{nickname}")
    public ResponseEntity<String> convertNicknameToUsername(@PathVariable("nickname") String nickname) {

        return ResponseEntity.ok(userService.convertNicknameToUsername(nickname));
    }


    @Operation(summary = "SMS 인증 요청 엔드포인트", description = "본인 인증을 위한 코드가 담긴 SMS를 발송하도록 요청한다.")
    @PostMapping("/send")
    public ResponseEntity<SingleMessageSentResponse> sendSmsValidate(@RequestBody @Valid UserSendSmsDto userSendSmsDto) {

        return ResponseEntity.ok(userService.sendSmsToVerify(userSendSmsDto));
    }

    @Operation(summary = "SMS 인증 확인 요청 엔드포인트", description = "요청자 정보와 입력한 코드를 전달받아 본인 인증 절차를 처리한다.")
    @PostMapping("/verify")
    public ResponseEntity<Boolean> redisVerify(@RequestBody @Valid UserSmsVerificationCheckDto smsVerificationCheckDto) {

        return ResponseEntity.ok(userService.verifyUser(smsVerificationCheckDto));
    }

    // 유저 삭제
    @Operation(summary = "사용자 삭제(비활성화) 엔드포인트", description = "Soft Delete 방식으로 사용자를 삭제한다.")
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@TokenUsername String username) {

        userService.deleteUser(username);

        return ResponseEntity.ok().build();
    }

}
