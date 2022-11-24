package s3_gps_ivanti.business.login;


import s3_gps_ivanti.dto.login.LoginRequestDTO;
import s3_gps_ivanti.dto.login.LoginResponseDTO;

public interface LoginUseCase {
    LoginResponseDTO login(LoginRequestDTO loginRequest);
}
