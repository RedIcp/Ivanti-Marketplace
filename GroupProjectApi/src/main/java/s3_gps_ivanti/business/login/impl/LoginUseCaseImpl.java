package s3_gps_ivanti.business.login.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.login.AccessTokenEncoder;
import s3_gps_ivanti.business.login.LoginUseCase;
import s3_gps_ivanti.business.exception.InvalidCredentialsException;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.dto.login.LoginRequestDTO;
import s3_gps_ivanti.dto.login.LoginResponseDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = userRepository.findUserByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new InvalidCredentialsException();
        }

        if (!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = generateAccessToken(user);
        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .build();
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(User user) {
        return accessTokenEncoder.encode(
                AccessTokenDTO.builder()
                        .subject(user.getUsername())
                        .roles(user.getRoles())
                        .userID(user.getId())
                        .build());
    }

}
