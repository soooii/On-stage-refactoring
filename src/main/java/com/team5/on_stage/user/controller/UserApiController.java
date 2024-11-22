package com.team5.on_stage.user.controller;

import com.team5.on_stage.user.dto.SignUpDto;
import com.team5.on_stage.user.dto.UpdateUserDto;
import com.team5.on_stage.user.repository.UserRepository;
import com.team5.on_stage.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserApiController {

    private final UserService userService;
    private final UserRepository userRepository;


    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(@Valid SignUpDto dto) {

        return ResponseEntity.ok(userService.signUp(dto));
    }


    // 유저 삭제
    @DeleteMapping("/{email}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("email") String email) {

        return ResponseEntity.ok(userRepository.deleteUserByEmail(email));
    }


    // 유저 정보 수정
    // Todo: 수정 항목 별로 구분할 것
    @PatchMapping("/{email}")
    public ResponseEntity<Boolean> updateUserInformation(@PathVariable("email") String email,
                                                         UpdateUserDto updateUserDto) {

        return ResponseEntity.ok(userService.updateUserInformation(email, updateUserDto));
    }

}
