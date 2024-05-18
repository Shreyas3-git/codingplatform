package com.crio.codingplateform.service;

import com.crio.codingplateform.dto.UserDto;
import com.crio.codingplateform.entity.User;
import com.crio.codingplateform.model.UserRegistrationRequest;
import com.crio.codingplateform.repository.UserRepository;
import com.crio.codingplateform.utils.ErrorCode;
import com.crio.codingplateform.utils.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<UserDto> findAllUser() {
        List<User> users = userRepository.findAll().stream()
                .sorted((u1, u2) -> Integer.compare(u2.getScore(), u1.getScore()))
                .toList();
        logger.debug("Total users fetched: {}", users.size());
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto findByUserId(long userId) {
        User user =  userRepository.findById(userId).orElseThrow(() -> {
           logger.error("User with ID '{}' not found: ",userId);
           return new UserException(ErrorCode.USER_NOT_FOUND,userId);
        });
        UserDto response = new UserDto();
        BeanUtils.copyProperties(user,UserDto.class);
        return response;
    }

    @Override
    public void registerUser(UserRegistrationRequest request) {
        logger.debug("Creating new user with ID: {}",request.getUserId());
        User user = User.builder().username(request.getUsername()).score(0)
                .badges(new HashSet<>()).userId(request.getUserId()).build();
        userRepository.save(user);
    }

    @Override
    public UserDto updateScore(long userId,int score) {
        logger.debug("Updating score for user with ID: {}, new score: {}", userId, score);
        User user = userRepository.findById(userId).orElseThrow(() -> {
            logger.error("User with ID '{}' not found: ",userId);
            return new UserException(ErrorCode.USER_NOT_FOUND,userId);
        });
        user.setScore(score);
        user.assignBadges(user);
        UserDto response = new UserDto();
        BeanUtils.copyProperties(user,UserDto.class);
        return response;
    }

    @Override
    public void deleteUserById(long userId) {
        logger.debug("Deleting user with ID: {}", userId);
        UserDto user = findByUserId(userId);
        userRepository.deleteById(user.getUserId());
    }

}
