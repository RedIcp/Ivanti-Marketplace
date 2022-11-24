package s3_gps_ivanti.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3_gps_ivanti.dto.review.ReviewDTO;
import s3_gps_ivanti.dto.user.CustomerSmallDetailDTO;
import s3_gps_ivanti.dto.version.VersionDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDetailedInfoDTO {
    private String name;
    private String icon;
    private String description;
    private List<String> screenshots;
    private int totalDownloads;
    private double avgRating;
    private List<VersionDTO> versions;
    private List<ReviewDTO> reviews;
    private CustomerSmallDetailDTO creator;
    private boolean isDiscontinued;
}
