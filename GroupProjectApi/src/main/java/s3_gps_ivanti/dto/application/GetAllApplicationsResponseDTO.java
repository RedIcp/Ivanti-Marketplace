package s3_gps_ivanti.dto.application;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllApplicationsResponseDTO {
    private List<ApplicationBasicInfoDTO> applications;
}
