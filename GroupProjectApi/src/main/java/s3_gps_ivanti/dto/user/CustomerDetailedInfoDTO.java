package s3_gps_ivanti.dto.user;

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
public class CustomerDetailedInfoDTO {
    private String username;
    private String email;
    private String permission;
    private List<ApplicationBasicInfoDTO> applications;
}
