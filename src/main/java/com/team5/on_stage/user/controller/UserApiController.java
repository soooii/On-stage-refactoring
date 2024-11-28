package com.team5.on_stage.user.controller;

import com.team5.on_stage.user.dto.SignUpDto;
import com.team5.on_stage.user.dto.SignUpUserDto;
import com.team5.on_stage.user.dto.UpdateUserDto;
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


    // Todo: 아이디, 비밀번호 가입 방식의 필요성 재고.
    @PostMapping
    public ResponseEntity<Boolean> basicSignUp(@Valid @RequestBody SignUpDto signUpDto) {

        return ResponseEntity.ok(userService.signUp(signUpDto));
    }


    // TempUser -> User 회원가입
    // Todo: 추후 인증 정보에서 username 뽑아서 파라미터로 전달할 것
    @PostMapping("/{username}")
    public ResponseEntity<Boolean> signUp(@Valid @PathVariable("username") String username, SignUpUserDto dto) {

        return ResponseEntity.ok(userService.signUpUser(username, dto));
    }


    // 유저 정보 수정
    // Todo: 수정 항목 별로 구분할 것
    @PatchMapping("/{email}")
    public ResponseEntity<Boolean> updateUserInformation(@PathVariable("email") String email,
                                                         UpdateUserDto updateUserDto) {

        return ResponseEntity.ok(userService.updateUserInformation(email, updateUserDto));
    }


    // 유저 삭제
    @DeleteMapping("/{email}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("email") String email) {

        return ResponseEntity.ok(userService.deleteUser(email));
    }
}
