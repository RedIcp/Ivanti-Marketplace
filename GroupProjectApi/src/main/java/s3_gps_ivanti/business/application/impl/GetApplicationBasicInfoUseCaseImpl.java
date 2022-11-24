package s3_gps_ivanti.business.application.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.application.GetApplicationsBasicInfoUseCase;
import s3_gps_ivanti.business.dtoconvertor.ApplicationDTOConverter;
import s3_gps_ivanti.dto.application.ApplicationBasicInfoDTO;
import s3_gps_ivanti.dto.application.GetAllApplicationsRequestDTO;
import s3_gps_ivanti.dto.application.GetAllApplicationsResponseDTO;
import s3_gps_ivanti.repository.ApplicationRepository;
import s3_gps_ivanti.repository.entity.Application;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
@Transactional
public class GetApplicationBasicInfoUseCaseImpl implements GetApplicationsBasicInfoUseCase {

    private final ApplicationRepository applicationRepository;

  @Override
    public GetAllApplicationsResponseDTO getAllApplications(GetAllApplicationsRequestDTO request) {
        List<Application> results = null;

          if (StringUtils.hasText(request.getName())) {
            if (StringUtils.hasText(request.getSort())) {
                switch (request.getSort()) {
                    case "nameAsc" -> results = applicationRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc(request.getName());
                    case "nameDesc" -> results = applicationRepository.findAllByNameContainingIgnoreCaseOrderByNameDesc(request.getName());
                    case "popularity" -> results = applicationRepository.findAllByNameContainingIgnoreCaseOrderByTotalDownloadsDesc(request.getName());
                }
            } else {
                results = applicationRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc(request.getName());
            }
        } else {
            if (StringUtils.hasText(request.getSort())) {
                switch (request.getSort()) {
                    case "nameAsc" -> results = applicationRepository.findAllByOrderByNameAsc();
                    case "nameDesc" -> results = applicationRepository.findAllByOrderByNameDesc();
                    case "popularity" -> results = applicationRepository.findAllByOrderByTotalDownloadsDesc();
                }
            } else {
                results = applicationRepository.findAllByOrderByNameAsc();
            }
        }

        List<ApplicationBasicInfoDTO> applications = results
                .stream()
                .filter(application -> !application.isStatus())
                .map(ApplicationDTOConverter::convertToDTO)
                .toList();

        return GetAllApplicationsResponseDTO.builder()
                .applications(applications)
                .build();
    }
}
