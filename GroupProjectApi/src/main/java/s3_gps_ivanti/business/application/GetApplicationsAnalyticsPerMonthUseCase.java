package s3_gps_ivanti.business.application;

import s3_gps_ivanti.dto.application.ApplicationAnalyticsRequestDTO;
import s3_gps_ivanti.dto.application.ApplicationAnalyticsResponseDTO;

import java.util.List;

public interface GetApplicationsAnalyticsPerMonthUseCase {
    List<ApplicationAnalyticsResponseDTO> getApplicationAnalytics(ApplicationAnalyticsRequestDTO request);
}
