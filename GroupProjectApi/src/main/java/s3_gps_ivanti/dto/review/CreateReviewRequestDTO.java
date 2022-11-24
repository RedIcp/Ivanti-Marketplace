package s3_gps_ivanti.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequestDTO {
    @NotNull
    private String customerName;
    @NotNull
    private String applicationName;
    @NotNull
    private int rating;
    @NotNull
    private String title;
    @NotNull
    private String description;
}
