package com.sparta.todoparty.user.service;

import com.sparta.todoparty.user.dto.UserRequestDto;
import com.sparta.todoparty.user.entity.User;
import com.sparta.todoparty.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @Test
    void signUp() {
        // Given
        String username = "user";
        String password = "pw";

        UserRequestDto requestDto = new UserRequestDto(username, password);
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());
        given(passwordEncoder.encode(password)).willReturn("encodingPassWord");

        // When
        userService.signup(requestDto);

        // Then
        verify(userRepository, times(1)).findByUsername(username);
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(password);
    }
    class login {
        @Test
        void loginCorrectPassword() {
            // Given
            String username = "user";
            String password = "pw";

            UserRequestDto requestDto = new UserRequestDto(username, password);
            User user = new User(username, passwordEncoder.encode(password));
            given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
            given(passwordEncoder.matches(password, user.getPassword())).willReturn(true);

            // When
            userService.login(requestDto);

            // Then
        }

        @Test
        void loginWrongPassword() {
            // Given
            String username = "user";
            String password = "pw";

            UserRequestDto requestDto = new UserRequestDto(username, password);
            User user = new User(username, passwordEncoder.encode("Wrongpassword"));
            given(userRepository.findByUsername(username)).willReturn(Optional.of(user));
            given(passwordEncoder.matches(password, user.getPassword())).willReturn(false);

            // When
            userService.login(requestDto);

            // Then
            assertThrows(IllegalArgumentException.class, () -> userService.login(requestDto));
        }

        @Test
        void notExistUser() {
            // Given
            String username = "user";
            String password = "pw";

            UserRequestDto requestDto = new UserRequestDto(username, password);
            given(userRepository.findByUsername(username)).willReturn(Optional.empty());

            // When

            // Then
            assertThrows(IllegalArgumentException.class, () -> userService.login(requestDto));
        }
    }
}
