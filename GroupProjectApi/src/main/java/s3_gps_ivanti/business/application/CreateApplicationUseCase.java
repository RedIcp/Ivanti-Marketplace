package s3_gps_ivanti.business.application;

import s3_gps_ivanti.dto.application.CreateApplicationRequestDTO;
import s3_gps_ivanti.dto.application.CreateApplicationResponseDTO;

public interface CreateApplicationUseCase {
    CreateApplicationResponseDTO createApplications(CreateApplicationRequestDTO applicationRequestDTO);
}
