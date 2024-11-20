package com.team5.on_stage.user.service;

import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.user.dto.SignUpDto;
import com.team5.on_stage.user.entity.EmailDomain;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public Boolean signUp(SignUpDto signUpDto) {

//      1. 중복 이메일 검증
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new GlobalException(ErrorCode.EMAIL_DUPLICATED);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(signUpDto.getPassword());

        signUpDto.setPassword(encodedPassword);

        User user = User.builder()
                .nickname(signUpDto.getNickname())
                .description(signUpDto.getDescription())
                .email(signUpDto.getEmail())
                .emailDomain(EmailDomain.valueOf(extractDomain(signUpDto.getEmail())))
                .build();

        userRepository.save(user);

        return true;
    }





    public static String extractDomain(String email) {

        return email.substring(email.indexOf("@") + 1, email.lastIndexOf("."));
    }
}
