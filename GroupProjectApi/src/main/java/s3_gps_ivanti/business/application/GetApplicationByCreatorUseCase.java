package s3_gps_ivanti.business.application;

import s3_gps_ivanti.dto.creator.CreatorApplicationListDTO;

public interface GetApplicationByCreatorUseCase {
    CreatorApplicationListDTO getApplicationsByCreator(String username);
}
