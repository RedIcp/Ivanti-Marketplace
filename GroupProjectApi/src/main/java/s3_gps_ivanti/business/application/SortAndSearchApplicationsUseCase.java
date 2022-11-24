package s3_gps_ivanti.business.application;

import s3_gps_ivanti.dto.application.ApplicationBasicInfoDTO;
import s3_gps_ivanti.repository.entity.Application;

import java.util.List;

public interface SortAndSearchApplicationsUseCase {

    List<ApplicationBasicInfoDTO> getApplicationsByName(String name);
    void sortApplicationsByName(List<Application> applications, boolean ascending);
    void sortApplicationsByRating(List<Application> applications, boolean ascending);
}
