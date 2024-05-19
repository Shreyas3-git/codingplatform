package com.crio.codingplateform.service;

import com.crio.codingplateform.dto.CommonResponse;
import com.crio.codingplateform.dto.UserDto;
import com.crio.codingplateform.entity.User;
import com.crio.codingplateform.model.UserRegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface UserService
{
    public List<UserDto> findAllUser();
    public UserDto findByUserId(long userId);
    public CommonResponse registerUser(UserRegistrationRequest user);
    public UserDto updateScore(long userId, int score);
    public CommonResponse deleteUserById(long userId);

}
