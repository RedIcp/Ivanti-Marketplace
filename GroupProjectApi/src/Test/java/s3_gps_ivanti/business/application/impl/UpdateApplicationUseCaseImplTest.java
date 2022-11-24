package s3_gps_ivanti.business.application.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.dtoconvertor.ApplicationDTOConverter;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.business.exception.InvalidAccessTokenException;
import s3_gps_ivanti.dto.application.UpdateApplicationRequestDTO;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;
import s3_gps_ivanti.repository.entity.RatingAnalytics;
import s3_gps_ivanti.repository.entity.User;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateApplicationUseCaseImplTest {

    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private AccessTokenDTO requestAccessToken;
    @InjectMocks
    private UpdateApplicationUseCaseImpl updateApplicationUseCase;

    @Test
    void updateApplications_ApplicationNotFound() {
        UpdateApplicationRequestDTO applicationRequestDTO = UpdateApplicationRequestDTO.builder()
                .username("applicationName")
                .build();
        when(applicationRepository.findByName("applicationName"))
                .thenReturn(null);

        assertThrows(ApplicationNotFoundException.class, () -> updateApplicationUseCase.updateApplications(applicationRequestDTO));

        verify(applicationRepository).findByName("applicationName");
    }
    @Test
    void updateApplications_IdNotMatch() {
        UpdateApplicationRequestDTO applicationRequestDTO = UpdateApplicationRequestDTO.builder()
                .username("applicationName")
                .description("description")
                .screenshots(List.of("One","Two"))
                .icon("icon")
                .build();

        Application application = Application.builder()
                .id("id")
                .creator(User.builder()
                        .id("id")
                        .username("creatorName")
                        .email("email")
                        .password("password")
                        .roles(List.of("role"))
                        .permission("permission")
                        .build())
                .name("name")
                .description("description")
                .icon("icon")
                .screenshots(List.of("one","two","three"))
                .status(false)
                .versions(Collections.emptyList())
                .rating(RatingAnalytics.builder()
                        .oneStar(0)
                        .twoStar(0)
                        .threeStar(0)
                        .fourStar(0)
                        .fiveStar(0)
                        .build())
                .build();

        when(applicationRepository.findByName("applicationName"))
                .thenReturn(application);
        when(requestAccessToken.getUserID())
                .thenReturn("newId");

        assertThrows(InvalidAccessTokenException.class, () -> updateApplicationUseCase.updateApplications(applicationRequestDTO));

        verify(applicationRepository).findByName("applicationName");
        verify(requestAccessToken).getUserID();

    }
    @Test
    void updateApplications() {
        UpdateApplicationRequestDTO applicationRequestDTO = UpdateApplicationRequestDTO.builder()
                .username("applicationName")
                .description("description")
                .screenshots(List.of("One","Two"))
                .icon("icon")
                .build();

        Application application = Application.builder()
                .id("id")
                .creator(User.builder()
                        .id("id")
                        .username("creatorName")
                        .email("email")
                        .password("password")
                        .roles(List.of("role"))
                        .permission("permission")
                        .build())
                .name("name")
                .description("description")
                .icon("icon")
                .screenshots(List.of("one","two","three"))
                .status(false)
                .versions(Collections.emptyList())
                .rating(RatingAnalytics.builder()
                        .oneStar(0)
                        .twoStar(0)
                        .threeStar(0)
                        .fourStar(0)
                        .fiveStar(0)
                        .build())
                .build();

        Application newApplication = ApplicationDTOConverter.convertToEntityForUpdate(applicationRequestDTO, application);


        when(applicationRepository.findByName("applicationName"))
                .thenReturn(application);
        when(requestAccessToken.getUserID())
                .thenReturn("id");
        when(applicationRepository.save(newApplication))
                .thenReturn(newApplication);

        updateApplicationUseCase.updateApplications(applicationRequestDTO);


        verify(applicationRepository).findByName("applicationName");
        verify(requestAccessToken).getUserID();
        verify(applicationRepository).save(newApplication);
    }
}