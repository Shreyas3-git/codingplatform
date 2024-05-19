package com.crio.codingplateform.service;

import com.crio.codingplateform.dto.CommonResponse;
import com.crio.codingplateform.dto.UserDto;
import com.crio.codingplateform.entity.Badges;
import com.crio.codingplateform.entity.User;
import com.crio.codingplateform.model.UserRegistrationRequest;
import com.crio.codingplateform.repository.UserRepository;
import com.crio.codingplateform.utils.ErrorCode;
import com.crio.codingplateform.utils.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllUser() {
        User user1 = new User(1L, "user1", 100, new HashSet<>());
        User user2 = new User(2L, "user2", 200, new HashSet<>());
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserDto> users = userService.findAllUser();

        assertEquals(2, users.size());
        assertEquals("user2", users.get(0).getUsername());
        assertEquals("user1", users.get(1).getUsername());
    }

    @Test
    public void testFindByUserId_UserExists() {
        User user = new User(1L, "user1", 100, new HashSet<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto userDto = userService.findByUserId(1L);

        assertNotNull(userDto);
        assertEquals("user1", userDto.getUsername());
    }

    @Test
    public void testFindByUserId_UserNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> userService.findByUserId(1L));
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        UserRegistrationRequest request = new UserRegistrationRequest(1L, "user1");
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        UserException exception = assertThrows(UserException.class, () -> userService.registerUser(request));
        assertEquals(ErrorCode.USER_ALREADY_EXISTS, exception.getErrorCode());
    }

    @Test
    public void testRegisterUser_Success() {
        UserRegistrationRequest request = new UserRegistrationRequest(1L, "user1");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(new User());

        CommonResponse response = userService.registerUser(request);

        assertEquals("Success", response.getStatus());
        assertEquals("User registration successful", response.getMessage());
        assertEquals("00", response.getErrorCode());
    }

    @Test
    public void testUpdateScore_UserExists() {
        User user = new User(1L, "user1", 50, new HashSet<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto userDto = userService.updateScore(1L, 70);

        assertNotNull(userDto);
        assertEquals(70, userDto.getScore());
        assertTrue(userDto.getBadges().contains(Badges.CODE_MASTER));
    }

    @Test
    public void testUpdateScore_UserNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> userService.updateScore(1L, 70));
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    public void testDeleteUserById_UserExists() {
        User user = new User(112L, "user1", 0, new HashSet<>());
        when(userRepository.findById(112L)).thenReturn(Optional.of(user));

        CommonResponse response = userService.deleteUserById(112L);

        verify(userRepository, times(1)).deleteById(112L);
        assertEquals("Success", response.getStatus());
        assertEquals("User with ID 112 deleted successfully", response.getMessage());
        assertEquals("00", response.getErrorCode());
    }

    @Test
    public void testDeleteUserById_UserNotExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> userService.deleteUserById(1L));
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    public void testDeleteUserById_UserExistsButExceptionOccursDuringDeletion() {
        User user = new User(112L, "user1", 0, new HashSet<>());
        when(userRepository.findById(112L)).thenReturn(Optional.of(user));
        doThrow(new RuntimeException("Unexpected error")).when(userRepository).deleteById(112L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteUserById(112L));
        assertEquals("Unexpected error", exception.getMessage());
    }
}
