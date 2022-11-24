package s3_gps_ivanti.business.login.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import s3_gps_ivanti.business.exception.InvalidCredentialsException;
import s3_gps_ivanti.business.login.AccessTokenEncoder;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.dto.login.LoginRequestDTO;
import s3_gps_ivanti.dto.login.LoginResponseDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccessTokenEncoder accessTokenEncoder;
    @InjectMocks
    private LoginUseCaseImpl  loginUseCaseImpl;


    @Test
    void login_NothingFound() {
        LoginRequestDTO login = LoginRequestDTO
                .builder()
                .username("user")
                .password("user")
                .build();

        when(userRepository.findUserByUsername("user"))
                .thenReturn(null);

        assertThrows(InvalidCredentialsException.class, () -> loginUseCaseImpl.login(login));

        verify(userRepository).findUserByUsername("user");
    }

    @Test
    void login_NormalUserFound_Pass() {
        User user = User.builder()
                .id("id")
                .username("creatorName")
                .email("email")
                .password("password")
                .roles(List.of("role"))
                .permission("permission")
                .build();

        LoginRequestDTO login = LoginRequestDTO
                .builder()
                .username("user")
                .password("user")
                .build();

        when(userRepository.findUserByUsername("user"))
                .thenReturn(user);
        when(passwordEncoder.matches("user","password"))
                .thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> loginUseCaseImpl.login(login));

        verify(userRepository).findUserByUsername("user");
        verify(passwordEncoder).matches("user","password");
    }

    @Test
    void login_NormalUserFound() {

        User user = User.builder()
                .id("id")
                .username("creatorName")
                .email("email")
                .password("password")
                .roles(List.of("role"))
                .permission("permission")
                .build();

        LoginRequestDTO login = LoginRequestDTO
                .builder()
                .username("user")
                .password("password")
                .build();

        when(userRepository.findUserByUsername("user"))
                .thenReturn(user);
        when(passwordEncoder.matches("password","password"))
                .thenReturn(true);

        LoginResponseDTO actualResult = loginUseCaseImpl.login(login);

        String accessToken = accessTokenEncoder.encode(
                AccessTokenDTO.builder()
                        .subject(user.getUsername())
                        .roles(user.getRoles())
                        .userID(user.getId())
                        .build());
        LoginResponseDTO expectedResult =LoginResponseDTO.builder()
                .accessToken(accessToken)
                .build();

        assertEquals(expectedResult,actualResult);

        verify(userRepository).findUserByUsername("user");
        verify(passwordEncoder).matches("password","password");
    }
}