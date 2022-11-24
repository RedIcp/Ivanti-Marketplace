package s3_gps_ivanti.business.review;

import s3_gps_ivanti.dto.review.CreateReviewRequestDTO;
import s3_gps_ivanti.dto.review.CreateReviewResponseDTO;

public interface CreateReviewUseCase {
    CreateReviewResponseDTO createReview(CreateReviewRequestDTO request);
}
