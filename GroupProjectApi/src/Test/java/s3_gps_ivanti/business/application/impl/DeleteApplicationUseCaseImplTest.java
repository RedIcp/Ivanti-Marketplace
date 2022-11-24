package s3_gps_ivanti.business.application.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.business.exception.InvalidAccessTokenException;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;
import s3_gps_ivanti.repository.entity.RatingAnalytics;
import s3_gps_ivanti.repository.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteApplicationUseCaseImplTest {

    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private AccessTokenDTO requestAccessToken;

    @InjectMocks
    private DeleteApplicationUseCaseImpl deleteApplicationUseCase;

    @Test
    void deleteApplications_ApplicationNotFound() {

        assertThrows(ApplicationNotFoundException.class, () -> deleteApplicationUseCase.deleteApplications("applicationID"));

        verify(applicationRepository).findById("applicationID");
    }

    @Test
    void deleteApplications_UserIDNotMatch() {

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

        when(applicationRepository.findById("applicationID"))
                .thenReturn(Optional.ofNullable(application));
        when(requestAccessToken.getUserID())
                .thenReturn("notId");

        assertThrows(InvalidAccessTokenException.class, () -> deleteApplicationUseCase.deleteApplications("applicationID"));

        verify(applicationRepository).findById("applicationID");
        verify(requestAccessToken).getUserID();
    }

    @Test
    void deleteApplications() {

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

        when(applicationRepository.findById("applicationID"))
                .thenReturn(Optional.ofNullable(application));
        when(requestAccessToken.getUserID())
                .thenReturn("id");

        deleteApplicationUseCase.deleteApplications("applicationID");

        verify(applicationRepository).findById("applicationID");
        verify(requestAccessToken).getUserID();
    }
}