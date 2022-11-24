package s3_gps_ivanti.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3_gps_ivanti.dto.response.ReplyDTO;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    @NotNull
    private String id;
    @NotNull
    private String customerName;
    @NotNull
    private double rating;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String timePassed;
    private ReplyDTO reply;
}
