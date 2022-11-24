package s3_gps_ivanti.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewDTO {
    private String id;
    private int rating;
    private String title;
    private String description;
}
