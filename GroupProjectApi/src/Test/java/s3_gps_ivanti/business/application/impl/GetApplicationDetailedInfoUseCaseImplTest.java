package s3_gps_ivanti.business.application.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.dtoconvertor.ApplicationDTOConverter;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.dto.application.ApplicationDetailedInfoDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetApplicationDetailedInfoUseCaseImplTest {

    @Mock
    private ApplicationRepository applicationRepository;
    @InjectMocks
    private GetApplicationDetailedInfoUseCaseImpl getApplicationDetailedInfoUseCase;

    @Test
    void getApplicationInfo_ApplicationNotFound() {

        when(applicationRepository.findByName("applicationName"))
                .thenReturn(null);

        assertThrows(ApplicationNotFoundException.class,() ->  getApplicationDetailedInfoUseCase.getApplicationInfo("applicationName"));

        verify(applicationRepository).findByName("applicationName");
    }

    /* fixme error in test
    @Test
    void getApplicationInfo() {

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
                .reviews(List.of(
                        Review.builder().id("one").applicationName("name").customer(User.builder().username("username").build()).rating(5).title("title").description("description").build(),
                        Review.builder().id("one").applicationName("name").customer(User.builder().username("username").build()).rating(5).title("title").description("description").build()))
                .versions(List.of(
                        Version.builder().number(1.0).totalDownloads(100).appLocation("appLocation").downloads(List.of(
                                DownloadsPerMonth.builder().year(2021).month("december").amount(70).build(),
                                DownloadsPerMonth.builder().year(2022).month("january").amount(30).build())).build(),
                        Version.builder().number(1.1).totalDownloads(400).appLocation("appLocation").downloads(List.of(
                                DownloadsPerMonth.builder().year(2021).month("january").amount(100).build(),
                                DownloadsPerMonth.builder().year(2022).month("february").amount(300).build())).build()))
                .rating(RatingAnalytics.builder()
                        .oneStar(3)
                        .twoStar(4)
                        .threeStar(4)
                        .fourStar(4)
                        .fiveStar(4)
                        .build())
                .build();

        when(applicationRepository.findByName("applicationName"))
                .thenReturn(application);

        ApplicationDetailedInfoDTO actualResult = getApplicationDetailedInfoUseCase.getApplicationInfo("applicationName");
        ApplicationDetailedInfoDTO expectedResult = ApplicationDTOConverter.convertToApplicationDetailedInfo(application);

        assertEquals(expectedResult, actualResult);

        verify(applicationRepository).findByName("applicationName");
    }*/
}