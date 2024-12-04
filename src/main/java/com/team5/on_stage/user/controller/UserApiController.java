package com.team5.on_stage.user.controller;

import com.team5.on_stage.global.config.jwt.TokenUsername;
import com.team5.on_stage.linklike.service.LinkLikeService;
import com.team5.on_stage.user.dto.UserProfileDto;
import com.team5.on_stage.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserApiController {

    private final UserService userService;
    private final LinkLikeService linkLikeService;


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


    @PatchMapping("/profile")
    public ResponseEntity<UserProfileDto> updateUserProfileImage(@TokenUsername String username,
                                                         MultipartFile profileImage) throws IOException {

        return ResponseEntity.ok(userService.updateUserProfileImage(username, profileImage));
    }


    // 좋아요 기능
    @PostMapping("/like/{userId}")
    public ResponseEntity<Boolean> likeLink(@PathVariable("userId") Long userId, Long linkId) {

        return ResponseEntity.ok(linkLikeService.likeLink(userId, linkId));
    }


    // 본인 프로필 정보 조회
    @GetMapping
    public ResponseEntity<UserProfileDto> getMyProfile(@TokenUsername String username) {

        return ResponseEntity.ok(userService.getUserProfile(username));
    }


    // 타인 방문 시 프로필 조회
    @GetMapping("/{username}")
    public ResponseEntity<UserProfileDto> getOtherProfiles(@PathVariable String username) {

        return ResponseEntity.ok(userService.getUserProfile(username));
    }


    // nickname -> username 변환
    @GetMapping("/convert/{nickname}")
    public ResponseEntity<String> convertNicknameToUsername(@PathVariable("nickname") String nickname) {

        return ResponseEntity.ok(userService.convertNicknameToUsername(nickname));
    }



    // 유저 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@TokenUsername String username) {

        userService.deleteUser(username);

        return ResponseEntity.ok().build();
    }


//    @PostMapping("/{username}")
//    public ResponseEntity<Void> likeUser(@TokenUsername String username,
//                                         @PathVariable("linkId") Long linkId) {
//
//        return ResponseEntity.ok(userService.likeLink(username, linkId));
//    }
}
