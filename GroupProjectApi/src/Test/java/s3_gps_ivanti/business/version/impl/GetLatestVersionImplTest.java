package s3_gps_ivanti.business.version.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.dtoconvertor.VersionDTOConverter;
import s3_gps_ivanti.business.exception.ApplicationHasNoVersionException;
import s3_gps_ivanti.dto.version.VersionDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;
import s3_gps_ivanti.repository.entity.RatingAnalytics;
import s3_gps_ivanti.repository.entity.User;
import s3_gps_ivanti.repository.entity.Version;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetLatestVersionImplTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private GetLatestVersionImpl getLatestVersion;

    @Test
    void getLatestVersion_VersionsSize_0() {
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
                .versions(Collections.emptyList())
                .rating(ratingAnalytics)
                .build();

        when(applicationRepository.findByName("applicationName"))
                .thenReturn(application);

       assertThrows(ApplicationHasNoVersionException.class, () -> getLatestVersion.getLatestVersion("applicationName"));

        verify(applicationRepository).findByName("applicationName");
    }

    @Test
    void getLatestVersion() {
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
                        Version.builder().number(2.0).totalDownloads(0).appLocation("appLocation").build(),
                        Version.builder().number(1.1).totalDownloads(0).appLocation("appLocation").build()))
                .rating(ratingAnalytics)
                .build();

        Version actualResult = getLatestVersion.getLatestVersion(application);
        Version expectedResult = Version.builder().number(2.0).totalDownloads(0).appLocation("appLocation").build();


        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetLatestVersion() {
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
                        Version.builder().number(1.1).totalDownloads(0).appLocation("appLocation").build()))
                .rating(ratingAnalytics)
                .build();

        when(applicationRepository.findByName("applicationName"))
                .thenReturn(application);

        VersionDTO actualResult = getLatestVersion.getLatestVersion("applicationName");
        VersionDTO expectedResult = VersionDTOConverter.convertToDTO( Version.builder().number(1.1).totalDownloads(0).appLocation("appLocation").build());

        assertEquals(expectedResult, actualResult);

        verify(applicationRepository).findByName("applicationName");
    }

}