package s3_gps_ivanti.business.version.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.dtoconvertor.VersionDTOConverter;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.business.exception.InvalidCredentialsException;
import s3_gps_ivanti.business.exception.VersionNumberIncorrectException;
import s3_gps_ivanti.business.version.CreateMajorVersionUseCase;
import s3_gps_ivanti.business.version.GetLatestVersionUseCase;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.dto.version.CreateMajorVersionRequestDTO;
import s3_gps_ivanti.dto.version.CreateMajorVersionResponseDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;
import s3_gps_ivanti.repository.entity.Version;

import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class CreateMajorVersionUseCaseImpl implements CreateMajorVersionUseCase {

    private final ApplicationRepository applicationRepository;
    private final GetLatestVersionUseCase getLatestVersion;
    private final AccessTokenDTO requestAccessToken;

    @Override
    public CreateMajorVersionResponseDTO createVersion(CreateMajorVersionRequestDTO versionRequestDTO) {

        Application application = applicationRepository.findByName(versionRequestDTO.getAppName());

        if(application == null) {
            throw new ApplicationNotFoundException();
        }

        if(!requestAccessToken.getUserID().equals(application.getCreator().getId())){
            throw new InvalidCredentialsException();
        }

        List<Version> newVersions = new ArrayList<>();
        newVersions.addAll(application.getVersions());
        Version version = VersionDTOConverter.convertToEntityForCreate(versionRequestDTO);
        newVersions.add(version);
        application.setVersions(newVersions);

        applicationRepository.save(application);

        return VersionDTOConverter.convertToDTOForMajorResponse(version);
    }
}
