package com.team5.on_stage.user.controller;

import com.team5.on_stage.user.dto.SignUpDto;
import com.team5.on_stage.user.dto.UpdateUserDto;
import com.team5.on_stage.user.repository.UserRepository;
import com.team5.on_stage.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/api/user")
@Controller
public class UserApiController {

    private final UserService userService;
    private final UserRepository userRepository;


    @PostMapping("/signup")
    public ResponseEntity<Boolean> signUp(@Valid @RequestBody SignUpDto dto) {

        return ResponseEntity.ok(userService.signUp(dto));
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("email") String email) {

        return ResponseEntity.ok(userRepository.deleteUserByEmail(email));
    }


    @PatchMapping("/{email}")
    public ResponseEntity<Boolean> updateUserInformation(@PathVariable("email") String email,
                                                         @RequestBody UpdateUserDto updateUserDto) {

        return ResponseEntity.ok(userService.updateUserInformation(email, updateUserDto));
    }

}
