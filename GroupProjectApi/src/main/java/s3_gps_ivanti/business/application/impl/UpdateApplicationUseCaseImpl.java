package s3_gps_ivanti.business.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.application.UpdateApplicationUseCase;
import s3_gps_ivanti.business.dtoconvertor.ApplicationDTOConverter;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.business.exception.InvalidAccessTokenException;
import s3_gps_ivanti.dto.application.UpdateApplicationRequestDTO;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;

import javax.transaction.Transactional;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class UpdateApplicationUseCaseImpl implements UpdateApplicationUseCase {

    private final ApplicationRepository applicationRepository;
    private final AccessTokenDTO requestAccessToken;

    @Override
    public void updateApplications(UpdateApplicationRequestDTO applicationRequestDTO) {

        Application application = applicationRepository.findByName(applicationRequestDTO.getUsername());

        if(application == null) {
            throw new ApplicationNotFoundException();
        }

        if(!requestAccessToken.getUserID().equals(application.getCreator().getId())){
            throw new InvalidAccessTokenException("Unauthorized");
        }

        Application newApplication = ApplicationDTOConverter.convertToEntityForUpdate(applicationRequestDTO, application);
        newApplication.setDownloads(application.getDownloads());
        newApplication.setReviewsPerMonths(application.getReviewsPerMonths());

        applicationRepository.save(newApplication);
    }
}
