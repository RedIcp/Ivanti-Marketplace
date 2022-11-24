package s3_gps_ivanti.business.review;

import s3_gps_ivanti.dto.review.CreateUpdateDeleteReplyDTO;
import s3_gps_ivanti.dto.review.UpdateReviewDTO;

public interface UpdateReviewUseCase {
    void updateReview(UpdateReviewDTO review);
    void replyAction(CreateUpdateDeleteReplyDTO request);
}
