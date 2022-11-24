package s3_gps_ivanti.business.version.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.dtoconvertor.VersionDTOConverter;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.business.exception.InvalidCredentialsException;
import s3_gps_ivanti.business.exception.VersionNumberIncorrectException;
import s3_gps_ivanti.business.version.GetLatestVersionUseCase;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.dto.version.CreateMinorVersionRequestDTO;
import s3_gps_ivanti.dto.version.CreateMinorVersionResponseDTO;
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
class CreateMinorVersionUseCaseImplTest {

    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private GetLatestVersionUseCase getLatestVersion;
    @Mock
    private AccessTokenDTO requestAccessToken;
    @InjectMocks
    private CreateMinorVersionUseCaseImpl createMinorVersionUseCase;

    @Test
    void createVersion_ApplicationNotFound() {

        CreateMinorVersionRequestDTO requestDTO = CreateMinorVersionRequestDTO.builder()
                .applicationID("id")
                .number(1.1)
                .appLocation("appLocation")
                .build();

        assertThrows(ApplicationNotFoundException.class, () -> createMinorVersionUseCase.createVersion(requestDTO));

        verify(applicationRepository).findById("id");

    }

    @Test
    void createVersion_UserIdNotMatch() {

        CreateMinorVersionRequestDTO requestDTO = CreateMinorVersionRequestDTO.builder()
                .applicationID("id")
                .number(1.1)
                .appLocation("appLocation")
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

        when(applicationRepository.findById("id"))
                .thenReturn(Optional.ofNullable(application));
        when(requestAccessToken.getUserID())
                .thenReturn("WrongID");

        assertThrows(InvalidCredentialsException.class, () -> createMinorVersionUseCase.createVersion(requestDTO));

        verify(applicationRepository).findById("id");
        verify(requestAccessToken).getUserID();
    }

//    @Test
//    void createVersion_WrongNewVersionNumber() {
//
//        CreateMinorVersionRequestDTO requestDTO = CreateMinorVersionRequestDTO.builder()
//                .applicationID("id")
//                .number(1.1)
//                .appLocation("appLocation")
//                .build();
//
//        Application application = Application.builder()
//                .id("id")
//                .creator(User.builder()
//                        .id("id")
//                        .username("creatorName")
//                        .email("email")
//                        .password("password")
//                        .roles(List.of("role"))
//                        .permission("permission")
//                        .build())
//                .name("name")
//                .description("description")
//                .icon("icon")
//                .screenshots(List.of("one", "two", "three"))
//                .status(false)
//                .versions(List.of(
//                        Version.builder().number(1.0).totalDownloads(0).appLocation("appLocation").build(),
//                        Version.builder().number(1.1).totalDownloads(0).appLocation("appLocation").build()))
//                .rating(RatingAnalytics.builder()
//                        .oneStar(0)
//                        .twoStar(0)
//                        .threeStar(0)
//                        .fourStar(0)
//                        .fiveStar(0)
//                        .build())
//                .build();
//
//        when(applicationRepository.findById("id"))
//                .thenReturn(Optional.ofNullable(application));
//        when(requestAccessToken.getUserID())
//                .thenReturn("id");
//        when(getLatestVersion.getLatestVersion(application))
//                .thenReturn(Version.builder().number(1.1).totalDownloads(0).appLocation("appLocation").build());
//
//        assertThrows(VersionNumberIncorrectException.class, () -> createMinorVersionUseCase.createVersion(requestDTO));
//
//        verify(applicationRepository).findById("id");
//        verify(requestAccessToken).getUserID();
//        verify(getLatestVersion).getLatestVersion(application);
//    }
//
//    @Test
//    void createVersion() {
//
//        CreateMinorVersionRequestDTO requestDTO = CreateMinorVersionRequestDTO.builder()
//                .applicationID("id")
//                .number(1.2)
//                .appLocation("appLocation")
//                .build();
//
//        Application application = Application.builder()
//                .id("id")
//                .creator(User.builder()
//                        .id("id")
//                        .username("creatorName")
//                        .email("email")
//                        .password("password")
//                        .roles(List.of("role"))
//                        .permission("permission")
//                        .build())
//                .name("name")
//                .description("description")
//                .icon("icon")
//                .screenshots(List.of("one", "two", "three"))
//                .status(false)
//                .versions(List.of(
//                        Version.builder().number(1.0).totalDownloads(0).appLocation("appLocation").build(),
//                        Version.builder().number(1.1).totalDownloads(0).appLocation("appLocation").build()))
//                .rating(RatingAnalytics.builder()
//                        .oneStar(0)
//                        .twoStar(0)
//                        .threeStar(0)
//                        .fourStar(0)
//                        .fiveStar(0)
//                        .build())
//                .build();
//
//
//        when(applicationRepository.findById("id"))
//                .thenReturn(Optional.ofNullable(application));
//        when(requestAccessToken.getUserID())
//                .thenReturn("id");
//        when(getLatestVersion.getLatestVersion(application))
//                .thenReturn(Version.builder().number(1.1).totalDownloads(0).appLocation("appLocation").build());
//
//        CreateMinorVersionResponseDTO actualResult = createMinorVersionUseCase.createVersion(requestDTO);
//        Version version = VersionDTOConverter.convertToEntityForCreate(requestDTO);
//        CreateMinorVersionResponseDTO expectedResult = VersionDTOConverter.convertToDTOForMinorResponse(version);
//
//        assertEquals(expectedResult, actualResult);
//
//        verify(applicationRepository).findById("id");
//        verify(requestAccessToken).getUserID();
//        verify(getLatestVersion).getLatestVersion(application);
//    }
}