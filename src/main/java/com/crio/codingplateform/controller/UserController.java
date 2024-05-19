package com.crio.codingplateform.controller;

import com.crio.codingplateform.dto.CommonResponse;
import com.crio.codingplateform.dto.UserDto;
import com.crio.codingplateform.entity.User;
import com.crio.codingplateform.model.UserRegistrationRequest;
import com.crio.codingplateform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAllUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userId) {
        UserDto user = userService.findByUserId(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        CommonResponse response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateScore(@PathVariable long userId, @RequestParam int score) {
        UserDto updatedUser = userService.updateScore(userId, score);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse> deleteUserById(@PathVariable long userId) {
        CommonResponse response = userService.deleteUserById(userId);
        return ResponseEntity.ok(response);
    }
}
