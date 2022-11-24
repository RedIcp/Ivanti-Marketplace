package s3_gps_ivanti.business.version.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.dtoconvertor.VersionDTOConverter;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.business.exception.InvalidCredentialsException;
import s3_gps_ivanti.business.version.UpdateVersionUseCase;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.dto.version.UpdateVersionRequestDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;
import s3_gps_ivanti.repository.entity.Version;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class UpdateVersionUseCaseImpl implements UpdateVersionUseCase {

    private final ApplicationRepository applicationRepository;
    private final AccessTokenDTO requestAccessToken;

    @Override
    public void updateVersion(UpdateVersionRequestDTO versionRequestDTO) {

        Application application = applicationRepository.findById(versionRequestDTO.getApplicationID()).orElse(null);

        if(application == null) {
            throw new ApplicationNotFoundException();
        }

        if(!requestAccessToken.getUserID().equals(application.getCreator().getId())){
            throw new InvalidCredentialsException();
        }

        List<Version> newVersions = new ArrayList<>();
        for (Version v: application.getVersions()) {
            if(v.getNumber() == versionRequestDTO.getNumber()){
                newVersions.add(VersionDTOConverter.convertToEntityForUpdate(versionRequestDTO,v));
            }
            else{
                newVersions.add(v);
            }
        }

        application.setVersions(newVersions);

        applicationRepository.save(application);
    }
}
