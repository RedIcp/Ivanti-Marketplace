package s3_gps_ivanti.business.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.application.GetApplicationDetailedInfoUseCase;
import s3_gps_ivanti.business.dtoconvertor.ApplicationDTOConverter;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.dto.application.ApplicationDetailedInfoDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;

import javax.transaction.Transactional;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class GetApplicationDetailedInfoUseCaseImpl implements GetApplicationDetailedInfoUseCase {

    private final ApplicationRepository applicationRepository;

    @Override
    public ApplicationDetailedInfoDTO getApplicationInfo(String name) {

        Application application = applicationRepository.findByName(name);

        if(application == null){
            throw new ApplicationNotFoundException();
        }

        return ApplicationDTOConverter.convertToApplicationDetailedInfo(application);
    }

}
