package s3_gps_ivanti.business.version.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.business.exception.InvalidCredentialsException;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;
import s3_gps_ivanti.repository.entity.RatingAnalytics;
import s3_gps_ivanti.repository.entity.User;
import s3_gps_ivanti.repository.entity.Version;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteVersionUseCaseImplTest {

    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private AccessTokenDTO requestAccessToken;
    @InjectMocks
    private DeleteVersionUseCaseImpl deleteVersionUseCase;

    @Test
    void deleteVersion_ApplicationNotFound() {

        assertThrows(ApplicationNotFoundException.class, () -> deleteVersionUseCase.deleteVersion("id",1.0));

        verify(applicationRepository).findById("id");
    }

    @Test
    void deleteVersion_UserIdNotMatch() {

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

        when(applicationRepository.findById("id"))
                .thenReturn(Optional.ofNullable(application));
        when(requestAccessToken.getUserID())
                .thenReturn("WrongID");

        assertThrows(InvalidCredentialsException.class, () -> deleteVersionUseCase.deleteVersion("id",1.0));

        verify(applicationRepository).findById("id");
        verify(requestAccessToken).getUserID();
    }

    @Test
    void deleteVersion() {

        User user = User.builder()
                .id("id")
                .username("creatorName")
                .email("email")
                .password("password")
                .roles(List.of("role"))
                .permission("permission")
                .build();
        RatingAnalytics ratingAnalytics = RatingAnalytics.builder()
                .oneStar(0)
                .twoStar(0)
                .threeStar(0)
                .fourStar(0)
                .fiveStar(0)
                .build();

        Application application = Application.builder()
                .id("id")
                .creator(user)
                .name("name")
                .description("description")
                .icon("icon")
                .screenshots(List.of("one", "two", "three"))
                .status(false)
                .versions(List.of(
                        Version.builder().number(1.0).totalDownloads(0).appLocation("appLocation").build(),
                        Version.builder().number(1.1).totalDownloads(0).appLocation("appLocation").build(),
                        Version.builder().number(2.0).totalDownloads(0).appLocation("appLocation").build(),
                        Version.builder().number(2.1).totalDownloads(0).appLocation("appLocation").build()))
                .rating(ratingAnalytics)
                .build();

        Application newApplication = application;
        newApplication.setVersions(List.of(
                Version.builder().number(1.1).totalDownloads(0).appLocation("appLocation").build(),
                Version.builder().number(2.0).totalDownloads(0).appLocation("appLocation").build(),
                Version.builder().number(2.1).totalDownloads(0).appLocation("appLocation").build()));

        when(applicationRepository.findById("id"))
                .thenReturn(Optional.ofNullable(application));
        when(requestAccessToken.getUserID())
                .thenReturn("id");
        when(applicationRepository.save(newApplication))
                .thenReturn(newApplication);

        deleteVersionUseCase.deleteVersion("id",1.0);

        verify(applicationRepository).findById("id");
        verify(requestAccessToken).getUserID();
        verify(applicationRepository).save(newApplication);
    }
}