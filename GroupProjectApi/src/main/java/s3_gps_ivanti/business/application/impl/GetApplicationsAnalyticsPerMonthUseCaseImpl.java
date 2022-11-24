package s3_gps_ivanti.business.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import s3_gps_ivanti.business.application.GetApplicationsAnalyticsPerMonthUseCase;
import s3_gps_ivanti.business.dtoconvertor.ApplicationDTOConverter;
import s3_gps_ivanti.business.validation.CustomerUsernameValidation;
import s3_gps_ivanti.dto.application.ApplicationAnalyticsRequestDTO;
import s3_gps_ivanti.dto.application.ApplicationAnalyticsResponseDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class GetApplicationsAnalyticsPerMonthUseCaseImpl implements GetApplicationsAnalyticsPerMonthUseCase {
    private final CustomerUsernameValidation customerIsValid;

    @Override
    public List<ApplicationAnalyticsResponseDTO> getApplicationAnalytics(ApplicationAnalyticsRequestDTO request) {
        User creator = customerIsValid.customerIsValid(request.getCreatorName());
        List<ApplicationAnalyticsResponseDTO> applicationAnalytics = new ArrayList<>();
        LocalDate date = LocalDate.now();

        if(StringUtils.hasText(String.valueOf(request.getYear()))){
            for (Application app : creator.getApplications()){
                ApplicationAnalyticsResponseDTO appAnalytics = appAnalyticsResponse(app, request.getYear());
                applicationAnalytics.add(appAnalytics);
            }
        }
        else
        for (Application app : creator.getApplications()){
            ApplicationAnalyticsResponseDTO appAnalytics = appAnalyticsResponse(app, date.getYear());
            applicationAnalytics.add(appAnalytics);
        }

        return applicationAnalytics;
    }

    private ApplicationAnalyticsResponseDTO appAnalyticsResponse(Application app, int year){
        ApplicationAnalyticsResponseDTO appAnalytics = ApplicationDTOConverter.convertToDTOForAnalytics(app);
        appAnalytics.setDownloads(getDownloadAnalyticsPerApp(app, year));
        appAnalytics.setTotalDownloads(totalDownloadPerYear(year, app));
        appAnalytics.setReviews(getReviewAnalyticsPerApp(app, year));
        appAnalytics.setTotalReviews(totalReviewsPerYear(year, app));

        return appAnalytics;
    }
    private List<DownloadsPerMonth> getDownloadAnalyticsPerApp(Application app, int year) {
        List<DownloadsPerMonth> downloadsAnalytics = new ArrayList<>();

        for (DownloadsPerMonth download : app.getDownloads()){
            if(download.getYear()==year){
                downloadsAnalytics.add(download);
            }
        }
        return downloadsAnalytics;
    }
    private int totalDownloadPerYear(int year, Application app){
        int amount = 0;

        for (DownloadsPerMonth downloads : getDownloadAnalyticsPerApp(app, year)){
            amount+=downloads.getAmount();
        }
        return amount;
    }


    private List<ReviewsPerMonth> getReviewAnalyticsPerApp(Application app, int year) {
        List<ReviewsPerMonth> reviewsAnalytics = new ArrayList<>();

        for (ReviewsPerMonth review : app.getReviewsPerMonths()){
            if(review.getYear()==year){
                reviewsAnalytics.add(review);
            }
        }
        return reviewsAnalytics;
    }
    private int totalReviewsPerYear(int year, Application app){
        int amount = 0;

        for (ReviewsPerMonth reviews : getReviewAnalyticsPerApp(app, year)){
            amount+=reviews.getAmount();
        }
        return amount;
    }
}
