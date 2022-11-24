package s3_gps_ivanti.dto.creator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3_gps_ivanti.dto.application.ApplicationBasicInfoDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatorApplicationListDTO {
    private List<ApplicationBasicInfoDTO> myApplications;
}
