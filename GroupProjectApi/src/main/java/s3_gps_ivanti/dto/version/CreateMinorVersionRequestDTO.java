package s3_gps_ivanti.dto.version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMinorVersionRequestDTO {
    private String appName;
    private double number;
    private String appLocation;
}
