package s3_gps_ivanti.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationAnalyticsRequestDTO {
    private String creatorName;
    private int year;
}
