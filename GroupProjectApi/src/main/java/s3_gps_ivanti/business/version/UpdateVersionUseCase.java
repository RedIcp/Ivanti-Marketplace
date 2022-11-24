package s3_gps_ivanti.business.version;

import s3_gps_ivanti.dto.version.UpdateVersionRequestDTO;

public interface UpdateVersionUseCase {
    void updateVersion(UpdateVersionRequestDTO versionRequestDTO);

}
