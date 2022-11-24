package s3_gps_ivanti.business.application.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.application.CreateApplicationUseCase;
import s3_gps_ivanti.business.dtoconvertor.ApplicationDTOConverter;
import s3_gps_ivanti.business.exception.ApplicationNameNotUniqueException;
import s3_gps_ivanti.business.exception.CantCreateApplicationException;
import s3_gps_ivanti.business.exception.InvalidAccessTokenException;
import s3_gps_ivanti.dto.application.CreateApplicationRequestDTO;
import s3_gps_ivanti.dto.application.CreateApplicationResponseDTO;
import s3_gps_ivanti.dto.login.AccessTokenDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.Application;
import s3_gps_ivanti.repository.entity.User;

import javax.transaction.Transactional;


@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class CreateApplicationUseCaseImpl implements CreateApplicationUseCase {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final AccessTokenDTO requestAccessToken;

    @Override
    public CreateApplicationResponseDTO createApplications(CreateApplicationRequestDTO applicationRequestDTO) {

        if (!requestAccessToken.hasRole("Creator")) {
            throw new InvalidAccessTokenException("Unauthorized");
        }

        if (applicationRepository.findByName(applicationRequestDTO.getName()) != null) {
            throw new ApplicationNameNotUniqueException();
        }
            if (applicationRepository.findByName(applicationRequestDTO.getName()) != null) {
                throw new ApplicationNameNotUniqueException();
            }

            Application app = ApplicationDTOConverter.convertToEntity(applicationRequestDTO);
            User user = userRepository.findUserByUsername(applicationRequestDTO.getCreatorID());

            if (user == null) {
                throw new CantCreateApplicationException();
            }

            if (!requestAccessToken.getUserID().equals(user.getId())) {
                throw new InvalidAccessTokenException("Unauthorized");
            }

            app.setCreator(user);
            Application newApplication = applicationRepository.save(app);
            return ApplicationDTOConverter.convertToDTOCreateResponse(newApplication);
        }
    }
