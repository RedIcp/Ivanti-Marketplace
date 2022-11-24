package s3_gps_ivanti.business.dtoconvertor;

import org.apache.commons.lang3.RandomStringUtils;
import s3_gps_ivanti.dto.application.*;
import s3_gps_ivanti.dto.review.CreateReviewRequestDTO;
import s3_gps_ivanti.dto.user.CustomerSmallDetailDTO;
import s3_gps_ivanti.repository.entity.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApplicationDTOConverter {

    public static CreateApplicationResponseDTO convertToDTOCreateResponse(Application application) {
        return CreateApplicationResponseDTO.builder()
                .id(application.getId())
                .build();
    }

    public static Application convertToEntity(CreateApplicationRequestDTO application) {
        return Application.builder()
                .id(RandomStringUtils.randomAlphanumeric(25))
                .name(application.getName())
                .description(application.getDescription())
                .icon(application.getIcon())
                .screenshots(application.getScreenshots())
                .status(false)
                .versions(List.of(Version.builder()
                        .number(1.0)
                        .totalReviews(0)
                        .totalDownloads(0)
                        .appLocation(application.getApplicationLocation())
                        .build()))
                .rating(new RatingAnalytics(0, 0, 0, 0, 0))
                .downloads(List.of(DownloadsPerMonth.builder()
                        .amount(0)
                        .month("")
                        .year(0)
                        .build()))
                .reviewsPerMonths(List.of(ReviewsPerMonth.builder()
                        .amount(0)
                        .month("")
                        .year(0)
                        .build()))
                .totalDownloads(0)
                .totalReviews(0)
                .build();
    }

    public static List<ApplicationBasicInfoDTO> convertListToDTO(List<Application> applications) {

        List<ApplicationBasicInfoDTO> result = new ArrayList<>();

        for (Application app : applications) {
            result.add(ApplicationDTOConverter.convertToDTO(app));
        }

        return result;
    }

    public static ApplicationBasicInfoDTO convertToDTO(Application app) {
        return ApplicationBasicInfoDTO.builder()
                .name(app.getName())
                .icon(app.getIcon())
                .build();
    }

    public static ApplicationDetailedInfoDTO convertToApplicationDetailedInfo(Application application) {

        int totalDownloads = 0;
        for (Version v : application.getVersions()) {
            totalDownloads += v.getTotalDownloads();
        }


        return ApplicationDetailedInfoDTO.builder()
                .name(application.getName())
                .icon(application.getIcon())
                .description(application.getDescription())
                .screenshots(application.getScreenshots())
                .totalDownloads(totalDownloads)
                .avgRating(application.getRating().avgStar())
                .versions(VersionDTOConverter.convertToListOfDTO(application.getVersions()))
                .reviews(ReviewDTOConverter.convertToListOfDTO(application.getReviews()))
                .creator(CustomerSmallDetailDTO.builder().id(application.getCreator().getId()).username(application.getCreator().getUsername()).build())
                .isDiscontinued(application.isStatus())
                .build();
    }

    public static Application convertToEntityForUpdate(UpdateApplicationRequestDTO app, Application application) {
        return Application.builder()
                .id(application.getId())
                .name(application.getName())
                .description(app.getDescription())
                .icon(app.getIcon())
                .creator(application.getCreator())
                .screenshots(app.getScreenshots())
                .status(application.isStatus() ?
                        true
                        : false)
                .reviews(application.getReviews())
                .versions(application.getVersions())
                .rating(application.getRating())
                .build();
    }

    public static ApplicationAnalyticsResponseDTO convertToDTOForAnalytics(Application app) {
        return ApplicationAnalyticsResponseDTO.builder()
                .name(app.getName())
                .icon(app.getIcon())
                .build();
    }

    private static ApplicationVersionAnalyticsDTO convertToDTOForVersionAnalytics(Application app) {
        return ApplicationVersionAnalyticsDTO.builder()
                .name(app.getName())
                .icon(app.getIcon())
                .totalDownloads(app.getTotalDownloads())
                .totalReviews(app.getTotalReviews())
                .versions(VersionDTOConverter.convertToVersionAnalyticsList(app.getVersions()))
                .build();
    }

    public static List<ApplicationVersionAnalyticsDTO> convertToDTOForVersionAnalyticsList(List<Application> apps) {
        List<ApplicationVersionAnalyticsDTO> result = new ArrayList<>();

        for (Application app : apps) {
            result.add(convertToDTOForVersionAnalytics(app));
        }
        return result;
    }
}
