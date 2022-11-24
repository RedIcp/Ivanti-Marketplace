package s3_gps_ivanti.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3_gps_ivanti.business.login.LoginUseCase;
import s3_gps_ivanti.dto.login.LoginRequestDTO;
import s3_gps_ivanti.dto.login.LoginResponseDTO;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class LoginController {

    private final LoginUseCase loginUseCase;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = loginUseCase.login(loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }
}