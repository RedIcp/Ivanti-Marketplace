package s3_gps_ivanti.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3_gps_ivanti.dto.version.VersionAnalyticsDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationVersionAnalyticsDTO {
    private String name;
    private String icon;
    private long totalDownloads;
    private long totalReviews;
    private List<VersionAnalyticsDTO> versions;
}
