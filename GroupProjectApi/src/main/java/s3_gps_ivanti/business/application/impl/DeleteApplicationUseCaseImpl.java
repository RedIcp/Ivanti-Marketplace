package s3_gps_ivanti.business.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.application.DeleteApplicationUseCase;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.business.exception.InvalidAccessTokenException;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;

import javax.transaction.Transactional;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class DeleteApplicationUseCaseImpl implements DeleteApplicationUseCase {

    private final ApplicationRepository applicationRepository;
    private final AccessTokenDTO requestAccessToken;

    @Override
    public void deleteApplications(String name) {

        Application application = applicationRepository.findByName(name);

        if(application == null) {
            throw new ApplicationNotFoundException();
        }

        if(!application.getCreator().getId().equals(requestAccessToken.getUserID())){
            throw new InvalidAccessTokenException("Unauthorized");
        }

        application.setStatus(!application.isStatus());

        applicationRepository.save(application);
    }
}
