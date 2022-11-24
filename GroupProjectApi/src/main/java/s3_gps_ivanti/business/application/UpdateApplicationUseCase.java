package s3_gps_ivanti.business.application;

import s3_gps_ivanti.dto.application.UpdateApplicationRequestDTO;

public interface UpdateApplicationUseCase {
    void updateApplications(UpdateApplicationRequestDTO applicationRequestDTO);
}
