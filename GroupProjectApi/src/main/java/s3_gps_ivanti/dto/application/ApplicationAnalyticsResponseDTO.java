package s3_gps_ivanti.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3_gps_ivanti.repository.entity.DownloadsPerMonth;
import s3_gps_ivanti.repository.entity.ReviewsPerMonth;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationAnalyticsResponseDTO {
    private String name;
    private String icon;
    private List<DownloadsPerMonth> downloads;
    private long totalDownloads;
    private List<ReviewsPerMonth> reviews;
    private long totalReviews;
}
