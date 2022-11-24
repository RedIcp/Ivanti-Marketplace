package s3_gps_ivanti.business.application.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.dtoconvertor.ApplicationDTOConverter;
import s3_gps_ivanti.business.exception.ApplicationNameNotUniqueException;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.business.exception.CantCreateApplicationException;
import s3_gps_ivanti.business.exception.InvalidAccessTokenException;
import s3_gps_ivanti.dto.application.CreateApplicationRequestDTO;
import s3_gps_ivanti.dto.application.CreateApplicationResponseDTO;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateApplicationUseCaseImplTest {

    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccessTokenDTO requestAccessToken;

    @InjectMocks
    private CreateApplicationUseCaseImpl createApplicationUseCase;

    @Test
    void createApplications_NotCreator() {
        CreateApplicationRequestDTO requestDTO = CreateApplicationRequestDTO.builder()
                .name("Name")
                .build();

        when(requestAccessToken.hasRole("Creator"))
                .thenReturn(false);

        assertThrows(InvalidAccessTokenException.class, () -> createApplicationUseCase.createApplications(requestDTO));

        verify(requestAccessToken).hasRole("Creator");
    }

    @Test
    void createApplications_ApplicationWithSameNameExists() {
        CreateApplicationRequestDTO requestDTO = CreateApplicationRequestDTO.builder()
                .name("Name")
                .build();

        Application application = Application.builder()
                .build();

        when(requestAccessToken.hasRole("Creator"))
                .thenReturn(true);
        when(applicationRepository.findByName("Name"))
                .thenReturn(application);

        assertThrows(ApplicationNameNotUniqueException.class, () -> createApplicationUseCase.createApplications(requestDTO));

        verify(requestAccessToken).hasRole("Creator");
        verify(applicationRepository).findByName("Name");
    }

    @Test
    void createApplications_UserNotFound() {
        CreateApplicationRequestDTO requestDTO = CreateApplicationRequestDTO.builder()
                .name("Name")
                .description("description")
                .screenshots(List.of("one","two","three"))
                .icon("icon")
                .applicationLocation("applicationLocation")
                .creatorID("creatorName")
                .build();

        when(requestAccessToken.hasRole("Creator"))
                .thenReturn(true);
        when(applicationRepository.findByName("Name"))
                .thenReturn(null);
        when(userRepository.findUserByUsername("creatorName"))
                .thenReturn(null);

        assertThrows(CantCreateApplicationException.class, () -> createApplicationUseCase.createApplications(requestDTO));

        verify(requestAccessToken).hasRole("Creator");
        verify(applicationRepository, times(2)).findByName("Name");
        verify(userRepository).findUserByUsername("creatorName");
    }

    @Test
    void createApplications_UsersIDNotMatch() {
        CreateApplicationRequestDTO requestDTO = CreateApplicationRequestDTO.builder()
                .name("Name")
                .description("description")
                .screenshots(List.of("one","two","three"))
                .icon("icon")
                .applicationLocation("applicationLocation")
                .creatorID("creatorName")
                .build();

        User user = User.builder()
                .id("id")
                .username("creatorName")
                .email("email")
                .password("password")
                .roles(List.of("role"))
                .permission("permission")
                .build();

        when(requestAccessToken.hasRole("Creator"))
                .thenReturn(true);
        when(applicationRepository.findByName("Name"))
                .thenReturn(null);
        when(userRepository.findUserByUsername("creatorName"))
                .thenReturn(user);
        when(requestAccessToken.getUserID())
                .thenReturn("thing");

        assertThrows(InvalidAccessTokenException.class, () -> createApplicationUseCase.createApplications(requestDTO));

        verify(requestAccessToken).hasRole("Creator");
        verify(applicationRepository, times(2)).findByName("Name");
        verify(userRepository).findUserByUsername("creatorName");
        verify(requestAccessToken).getUserID();
    }

    @Test
    void createApplications() {
        mockStatic(RandomStringUtils.class);

        CreateApplicationRequestDTO requestDTO = CreateApplicationRequestDTO.builder()
                .name("Name")
                .description("description")
                .screenshots(List.of("one","two","three"))
                .icon("icon")
                .applicationLocation("applicationLocation")
                .creatorID("creatorName")
                .build();

        User user = User.builder()
                .id("id")
                .username("creatorName")
                .email("email")
                .password("password")
                .roles(List.of("role"))
                .permission("permission")
                .build();

        when(RandomStringUtils.randomAlphanumeric(25))
                .thenReturn("one");

        Application application = ApplicationDTOConverter.convertToEntity(requestDTO);
        application.setCreator(user);

        when(requestAccessToken.hasRole("Creator"))
                .thenReturn(true);
        when(applicationRepository.findByName("Name"))
                .thenReturn(null);
        when(userRepository.findUserByUsername("creatorName"))
                .thenReturn(user);
        when(requestAccessToken.getUserID())
                .thenReturn("id");
        when(applicationRepository.save(application))
                .thenReturn(application);

        CreateApplicationResponseDTO actualResult = createApplicationUseCase.createApplications(requestDTO);
        CreateApplicationResponseDTO expectedResult = CreateApplicationResponseDTO.builder()
                .id(application.getId())
                .build();

        assertEquals(expectedResult, actualResult);

        verify(requestAccessToken).hasRole("Creator");
        verify(applicationRepository, times(2)).findByName("Name");
        verify(userRepository).findUserByUsername("creatorName");
        verify(requestAccessToken).getUserID();
        verify(applicationRepository).save(application);
    }
}