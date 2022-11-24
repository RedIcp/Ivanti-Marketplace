package s3_gps_ivanti.business.user.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.dtoconvertor.CustomerDTOConverter;
import s3_gps_ivanti.business.exception.CustomerNotFoundException;
import s3_gps_ivanti.dto.user.CustomerDetailedInfoDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCustomerUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private GetCustomerUseCaseImpl getCustomerUseCase;

    @Test
    void getCustomer_NotFound() {
        when(userRepository.findUserByUsername("username"))
                .thenReturn(null);

        assertThrows(CustomerNotFoundException.class, () -> getCustomerUseCase.getCustomer("username"));

        verify(userRepository).findUserByUsername("username");
    }

    @Test
    void getCustomer() {
        User user = User.builder()
                .id("id")
                .username("creatorName")
                .email("email")
                .password("password")
                .roles(List.of("role"))
                .permission("permission")
                .applications(Collections.emptyList())
                .build();

        when(userRepository.findUserByUsername("username"))
                .thenReturn(user);

        CustomerDetailedInfoDTO actualResult = getCustomerUseCase.getCustomer("username");
        CustomerDetailedInfoDTO expectedResult = CustomerDTOConverter.convertToDetailedDTO(user);

        assertEquals(expectedResult, actualResult);

        verify(userRepository).findUserByUsername("username");
    }
}