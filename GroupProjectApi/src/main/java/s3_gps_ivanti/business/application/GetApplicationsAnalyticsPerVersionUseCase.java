package s3_gps_ivanti.business.application;

import s3_gps_ivanti.dto.application.ApplicationVersionAnalyticsDTO;

import java.util.List;

public interface GetApplicationsAnalyticsPerVersionUseCase {
    List<ApplicationVersionAnalyticsDTO> getVersionAnalytics(String creator);
}
