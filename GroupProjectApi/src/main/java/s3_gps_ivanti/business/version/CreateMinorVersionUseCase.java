package s3_gps_ivanti.business.version;

import s3_gps_ivanti.dto.version.CreateMinorVersionRequestDTO;
import s3_gps_ivanti.dto.version.CreateMinorVersionResponseDTO;

public interface CreateMinorVersionUseCase {
    CreateMinorVersionResponseDTO createVersion(CreateMinorVersionRequestDTO versionRequestDTO);
}
