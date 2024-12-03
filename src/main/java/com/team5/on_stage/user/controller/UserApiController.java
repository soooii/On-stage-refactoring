package com.team5.on_stage.user.controller;

import com.team5.on_stage.global.config.jwt.TokenUsername;
import com.team5.on_stage.linklike.service.LinkLikeService;
import com.team5.on_stage.user.dto.UserProfileDto;
import com.team5.on_stage.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    // 닉네임 변경
//    @PatchMapping("/{nickname}")
//    public ResponseEntity<Boolean> updateUserNickname(@TokenUsername String username,
//                                                      @PathVariable String nickname) {
//
//        if (userService.checkNicknameDuplicated(nickname)) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//        else {
//            return ResponseEntity.ok(userService.updateUserNickname(username, nickname));
//        }
//    }


    // 자기 소개글 수정
//    @PatchMapping("/{description}")
//    public ResponseEntity<Boolean> updateUserDescription(@TokenUsername String username,
//                                                         @PathVariable String description) {
//
//        return ResponseEntity.ok(userService.updateUserDescription(username, description));
//    }


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
    public ResponseEntity<Boolean> deleteUser(@TokenUsername String username) {

        return ResponseEntity.ok(userService.deleteUser(username));
    }


//    @PostMapping("/{username}")
//    public ResponseEntity<Void> likeUser(@TokenUsername String username,
//                                         @PathVariable("linkId") Long linkId) {
//
//        return ResponseEntity.ok(userService.likeLink(username, linkId));
//    }
}
