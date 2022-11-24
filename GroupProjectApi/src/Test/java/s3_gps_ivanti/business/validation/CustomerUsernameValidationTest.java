package s3_gps_ivanti.business.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.exception.CustomerNotFoundException;
import s3_gps_ivanti.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerUsernameValidationTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CustomerUsernameValidation customerUsernameValidation;


    @Test
    void customerIsValid() {

        when(userRepository.existsByUsername("username"))
                .thenReturn(true);

        customerUsernameValidation.customerIsValid("username");

        verify(userRepository).existsByUsername("username");
    }

    @Test
    void customerIsValid_DosntExist() {

        when(userRepository.existsByUsername("username"))
                .thenReturn(false);

        assertThrows(CustomerNotFoundException.class,() -> customerUsernameValidation.customerIsValid("username"));

        verify(userRepository).existsByUsername("username");
    }
}