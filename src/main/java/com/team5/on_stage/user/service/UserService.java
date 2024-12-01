package com.team5.on_stage.user.service;

import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.user.entity.*;
import com.team5.on_stage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public Boolean updateUserNickname(String username,
                                      String nickname) {

        User user = userRepository.findByUsername(username);

        if (user == null) {

            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        user.setNickname(nickname);

        userRepository.save(user);

        return true;
    }


    public Boolean updateUserDescription(String username,
                                         String description) {

        User user = userRepository.findByUsername(username);

        if (user == null) {

            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        user.setDescription(description);

        userRepository.save(user);

        return true;
    }


    public Boolean deleteUser(String username) {

        if (userRepository.findByUsername(username) == null) {

            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        return userRepository.deleteUserByUsername(username);
    }


    // Context에서 메인 파라미터 username 추출
//    public String getUsername() {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
//
//        return oauth2User.getUsername();
//    }
}
