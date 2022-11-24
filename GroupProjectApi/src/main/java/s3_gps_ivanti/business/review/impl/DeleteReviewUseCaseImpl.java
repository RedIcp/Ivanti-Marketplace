package s3_gps_ivanti.business.review.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.review.DeleteReviewUseCase;
import s3_gps_ivanti.business.validation.ReviewIDValidation;
import s3_gps_ivanti.repository.ReviewRepository;
import s3_gps_ivanti.repository.entity.Review;

@Service
@Primary
@RequiredArgsConstructor
public class DeleteReviewUseCaseImpl implements DeleteReviewUseCase {
    private final ReviewRepository reviewRepository;
    private final ReviewIDValidation reviewIsValid;
    private final UpdateRating updateRating;

    @Override
    public void deleteReview(String id) {
        Review review = reviewIsValid.reviewInvalid(id);

        updateRating.subtractAppRating(review.getApplicationName(), review.getRating());

        reviewRepository.deleteById(id);
    }
}
