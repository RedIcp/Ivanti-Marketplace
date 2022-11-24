package s3_gps_ivanti.dto.version;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVersionRequestDTO {
    private String applicationID;
    private double number;
    private String appLocation;
}
