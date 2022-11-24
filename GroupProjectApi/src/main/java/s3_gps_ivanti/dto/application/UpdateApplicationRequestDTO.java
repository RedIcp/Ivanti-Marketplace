package s3_gps_ivanti.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationRequestDTO {
    private String username;
    private String description;
    private List<String> screenshots;
    private String icon;
}
