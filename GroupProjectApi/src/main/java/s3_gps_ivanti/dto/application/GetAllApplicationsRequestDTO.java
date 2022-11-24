package s3_gps_ivanti.dto.application;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAllApplicationsRequestDTO {
    private String name;

    @NotBlank
    private Boolean status;

    private String sort;
}
