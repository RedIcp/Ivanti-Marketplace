package s3_gps_ivanti.business.user.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import s3_gps_ivanti.business.dtoconvertor.CustomerDTOConverter;
import s3_gps_ivanti.business.exception.EmailAlreadyExistsException;
import s3_gps_ivanti.business.exception.UsernameAlreadyExistsException;
import s3_gps_ivanti.dto.user.CreateCustomerRequestDTO;
import s3_gps_ivanti.dto.user.CreateCustomerResponseDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCustomerUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private CreateCustomerUseCaseImpl createCustomerUseCase;

    @Test
    void createCustomer_UsernameNotUnique() {

        CreateCustomerRequestDTO requestDTO = CreateCustomerRequestDTO.builder()
                .username("username")
                .email("email")
                .password("password")
                .build();

        when(userRepository.findUserByUsername("username"))
                .thenReturn(User.builder().build());

        assertThrows(UsernameAlreadyExistsException.class,()-> createCustomerUseCase.createCustomer(requestDTO));

        verify(userRepository).findUserByUsername("username");
    }

    @Test
    void createCustomer_EmailNotUnique() {

        CreateCustomerRequestDTO requestDTO = CreateCustomerRequestDTO.builder()
                .username("username")
                .email("email")
                .password("password")
                .build();

        when(userRepository.findUserByUsername("username"))
                .thenReturn(null);
        when(userRepository.findUserByEmail("email"))
                .thenReturn(User.builder().build());

        assertThrows(EmailAlreadyExistsException.class,()-> createCustomerUseCase.createCustomer(requestDTO));

        verify(userRepository).findUserByUsername("username");
        verify(userRepository).findUserByEmail("email");
    }

    @Test
    void createCustomer() {
        CreateCustomerRequestDTO requestDTO = CreateCustomerRequestDTO.builder()
                .username("username")
                .email("email")
                .password("password")
                .build();

        User newUser = CustomerDTOConverter.convertToEntity(requestDTO);

        newUser.setPassword("newPassword");

        when(userRepository.findUserByUsername("username"))
                .thenReturn(null);
        when(userRepository.findUserByEmail("email"))
                .thenReturn(null);
        when(passwordEncoder.encode("password"))
                .thenReturn("newPassword");

        CreateCustomerResponseDTO actualResult = createCustomerUseCase.createCustomer(requestDTO);
        CreateCustomerResponseDTO expectedResult = CreateCustomerResponseDTO.builder()
                .username("username")
                .build();

        assertEquals(expectedResult, actualResult);

        verify(userRepository).findUserByUsername("username");
        verify(userRepository).findUserByEmail("email");
        verify(passwordEncoder).encode("password");
    }
}