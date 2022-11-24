package s3_gps_ivanti.business.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.application.GetApplicationsAnalyticsPerVersionUseCase;
import s3_gps_ivanti.business.dtoconvertor.ApplicationDTOConverter;
import s3_gps_ivanti.business.validation.CustomerUsernameValidation;
import s3_gps_ivanti.dto.application.ApplicationVersionAnalyticsDTO;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class GetApplicationsAnalyticsPerVersionUseCaseImpl implements GetApplicationsAnalyticsPerVersionUseCase {
    private final CustomerUsernameValidation userIsValid;

    @Override
    public List<ApplicationVersionAnalyticsDTO> getVersionAnalytics(String creator) {
        User user = userIsValid.customerIsValid(creator);

        return ApplicationDTOConverter.convertToDTOForVersionAnalyticsList(user.getApplications());
    }
}
