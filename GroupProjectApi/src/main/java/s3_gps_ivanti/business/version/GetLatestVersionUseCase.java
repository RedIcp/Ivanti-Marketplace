package s3_gps_ivanti.business.version;

import s3_gps_ivanti.dto.version.VersionDTO;
import s3_gps_ivanti.repository.entity.Application;
import s3_gps_ivanti.repository.entity.Version;

public interface GetLatestVersionUseCase {
    Version getLatestVersion(Application application);
    VersionDTO getLatestVersion(String applicationName);
}
