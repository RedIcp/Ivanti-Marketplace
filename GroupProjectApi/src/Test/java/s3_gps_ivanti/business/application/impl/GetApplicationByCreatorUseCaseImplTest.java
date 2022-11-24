package s3_gps_ivanti.business.application.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.dtoconvertor.ApplicationDTOConverter;
import s3_gps_ivanti.business.exception.CreatorNotFoundException;
import s3_gps_ivanti.business.exception.InvalidAccessTokenException;
import s3_gps_ivanti.dto.creator.CreatorApplicationListDTO;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.Application;
import s3_gps_ivanti.repository.entity.RatingAnalytics;
import s3_gps_ivanti.repository.entity.User;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetApplicationByCreatorUseCaseImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AccessTokenDTO requestAccessToken;
    @InjectMocks
    private GetApplicationByCreatorUseCaseImpl getApplicationByCreatorUseCase;

    @Test
    void getApplicationsByCreator_CreatorNotFound() {

        when(userRepository.findUserByUsername("username"))
                .thenReturn(null);

        assertThrows(CreatorNotFoundException.class,() ->  getApplicationByCreatorUseCase.getApplicationsByCreator("username"));

        verify(userRepository).findUserByUsername("username");
    }

    @Test
    void getApplicationsByCreator_UserIDNotMatched() {

        User user = User.builder()
                .id("id")
                .username("creatorName")
                .email("email")
                .password("password")
                .roles(List.of("role"))
                .permission("permission")
                .build();


        when(userRepository.findUserByUsername("username"))
                .thenReturn(user);

        when(requestAccessToken.getUserID())
                .thenReturn("notID");

        assertThrows(InvalidAccessTokenException.class,() ->  getApplicationByCreatorUseCase.getApplicationsByCreator("username"));

        verify(userRepository).findUserByUsername("username");
        verify(requestAccessToken).getUserID();
    }

    @Test
    void getApplicationsByCreator() {
        Application application1= Application.builder()
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

        Application application2 = Application.builder()
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

        User user = User.builder()
                .id("id")
                .username("creatorName")
                .email("email")
                .password("password")
                .roles(List.of("role"))
                .permission("permission")
                .applications(List.of(application1, application2))
                .build();


        when(userRepository.findUserByUsername("username"))
                .thenReturn(user);
        when(requestAccessToken.getUserID())
                .thenReturn("id");

        CreatorApplicationListDTO actualResult = getApplicationByCreatorUseCase.getApplicationsByCreator("username");

        CreatorApplicationListDTO expectedResult = CreatorApplicationListDTO.builder()
                .myApplications(ApplicationDTOConverter.convertListToDTO(user.getApplications()))
                .build();

        assertEquals(expectedResult, actualResult);

        verify(userRepository).findUserByUsername("username");
        verify(requestAccessToken).getUserID();
    }
}