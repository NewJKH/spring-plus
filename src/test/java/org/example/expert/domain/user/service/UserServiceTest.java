package org.example.expert.domain.user.service;

import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static java.lang.Math.log;
import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("유저이름_1건_조회_성공")
    void getUsersByNickName() {
        //g
        User user = mock(User.class);
        ReflectionTestUtils.setField(user,"id",1L);
        ReflectionTestUtils.setField(user,"nickname","장군호");

        when(userRepository.findByNickname("장군호")).thenReturn(Optional.of(user));
        //w
        UserResponse userResponse = userService.getUserByNickName("장군호");
        //t
        assertEquals(user.getId(), userResponse.getId());

    }
}