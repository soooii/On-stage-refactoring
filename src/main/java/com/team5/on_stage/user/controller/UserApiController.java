package com.team5.on_stage.user.controller;

import com.team5.on_stage.global.config.jwt.TokenUsername;
import com.team5.on_stage.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserApiController {

    private final UserService userService;


    @PatchMapping("/nickname")
    public ResponseEntity<Boolean> updateUserNickname(@TokenUsername String username,
                                                      String nickname) {

        return ResponseEntity.ok(userService.updateUserNickname(username, nickname));
    }


    @PatchMapping("/description")
    public ResponseEntity<Boolean> updateUserDescription(@TokenUsername String username,
                                                         String description) {

        return ResponseEntity.ok(userService.updateUserDescription(username, description));
    }


    // 유저 삭제
    @DeleteMapping("/{email}")
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
