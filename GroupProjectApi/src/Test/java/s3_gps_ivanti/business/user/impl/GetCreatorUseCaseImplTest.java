package s3_gps_ivanti.business.user.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCreatorUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private GetCreatorUseCaseImpl getCreatorUseCase;

    @Test
    void getCreator_UserNotFound() {
        when(userRepository.findUserByUsername("username"))
                .thenReturn(null);

       assertThrows(NotFoundException.class ,()->getCreatorUseCase.getCreator("username"));

        verify(userRepository).findUserByUsername("username");
    }

    @Test
    void getCreator_FoundNormalUser() {

        User user = User.builder()
                .roles(List.of("Customer"))
                .build();

        when(userRepository.findUserByUsername("username"))
                .thenReturn(user);

        assertThrows(NotFoundException.class ,()->getCreatorUseCase.getCreator("username"));

        verify(userRepository).findUserByUsername("username");
    }

    @Test
    void getCreator() {

        User user = User.builder()
                .roles(List.of("Creator"))
                .build();

        when(userRepository.findUserByUsername("username"))
                .thenReturn(user);

        User actualResult = getCreatorUseCase.getCreator("username");
        User expectedResult = user;

        assertEquals(expectedResult, actualResult);

        verify(userRepository).findUserByUsername("username");
    }
}