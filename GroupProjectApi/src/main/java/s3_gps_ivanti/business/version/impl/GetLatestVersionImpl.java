package s3_gps_ivanti.business.version.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.dtoconvertor.VersionDTOConverter;
import s3_gps_ivanti.business.exception.ApplicationHasNoVersionException;
import s3_gps_ivanti.business.version.GetLatestVersionUseCase;
import s3_gps_ivanti.dto.version.VersionDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;
import s3_gps_ivanti.repository.entity.Version;

import javax.transaction.Transactional;


@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class GetLatestVersionImpl implements GetLatestVersionUseCase {

    private final ApplicationRepository applicationRepository;

    @Override
    public Version getLatestVersion(Application application) {

        if(application.getVersions().size() <= 0){
            throw new ApplicationHasNoVersionException();
        }

        Version result = new Version();

        for (Version v: application.getVersions()) {
            if(v.getNumber() > result.getNumber())
            {
                result = v;
            }
        }
        return result;
    }

    @Override
    public VersionDTO getLatestVersion(String applicationName) {
        Version latestVersion = getLatestVersion(applicationRepository.findByName(applicationName));

        return VersionDTOConverter.convertToDTO(latestVersion);
    }
}
