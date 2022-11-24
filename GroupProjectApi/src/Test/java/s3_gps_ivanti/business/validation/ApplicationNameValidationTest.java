package s3_gps_ivanti.business.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.repository.ApplicationRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationNameValidationTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @InjectMocks
    private ApplicationNameValidation applicationNameValidation;

    @Test
    void applicationIsValid() {
        when(applicationRepository.existsByName("app")).thenReturn(true);

        applicationNameValidation.applicationIsValid("app");

        verify(applicationRepository).existsByName("app");
    }

    @Test
    void applicationIsInvalid() {
        when(applicationRepository.existsByName("app")).thenReturn(false);

        assertThrows(ApplicationNotFoundException.class, ()-> applicationNameValidation.applicationIsValid("app"));

        verify(applicationRepository).existsByName("app");
    }
}