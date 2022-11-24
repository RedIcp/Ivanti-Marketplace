package s3_gps_ivanti.business.version;

import s3_gps_ivanti.dto.version.CreateMajorVersionRequestDTO;
import s3_gps_ivanti.dto.version.CreateMajorVersionResponseDTO;

public interface CreateMajorVersionUseCase {
    CreateMajorVersionResponseDTO createVersion(CreateMajorVersionRequestDTO versionRequestDTO);
}
