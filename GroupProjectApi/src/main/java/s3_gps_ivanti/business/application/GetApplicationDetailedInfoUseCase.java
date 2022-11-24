package s3_gps_ivanti.business.application;

import s3_gps_ivanti.dto.application.ApplicationDetailedInfoDTO;

public interface GetApplicationDetailedInfoUseCase {

    ApplicationDetailedInfoDTO getApplicationInfo(String name);

//    Optional<ApplicationDetailedInfoDTO> getApplicationDetailedInfo(String name);
}
