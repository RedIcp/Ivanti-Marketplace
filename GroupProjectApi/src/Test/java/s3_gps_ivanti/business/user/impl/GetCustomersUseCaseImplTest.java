package s3_gps_ivanti.business.user.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.dtoconvertor.CustomerDTOConverter;
import s3_gps_ivanti.dto.user.UserBasicInfoDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCustomersUseCaseImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private GetCustomersUseCaseImpl getCustomersUseCase;

    @Test
    void getAllCustomers() {
        User user1 = User.builder()
                .id("id")
                .username("creatorName")
                .email("email")
                .password("password")
                .roles(List.of("role"))
                .permission("permission")
                .build();
        User user2 = User.builder()
                .id("id2")
                .username("creatorName2")
                .email("email2")
                .password("password2")
                .roles(List.of("role2"))
                .permission("permission2")
                .build();

        when(userRepository.findAll())
                .thenReturn(List.of(user1,user2));

        List<UserBasicInfoDTO> actualResult =  getCustomersUseCase.getAllCustomers();
        List<UserBasicInfoDTO> expectedResult =  CustomerDTOConverter.convertToListDTO(List.of(user1,user2));

        assertEquals(expectedResult, actualResult);

        verify(userRepository).findAll();
    }

}