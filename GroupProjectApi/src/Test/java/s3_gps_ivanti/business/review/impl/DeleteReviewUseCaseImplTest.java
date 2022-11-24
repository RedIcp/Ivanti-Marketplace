package s3_gps_ivanti.business.review.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.exception.InvalidReviewException;
import s3_gps_ivanti.business.validation.ReviewIDValidation;
import s3_gps_ivanti.repository.ReviewRepository;
import s3_gps_ivanti.repository.entity.Review;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteReviewUseCaseImplTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewIDValidation reviewIsValid;
    @Mock
    private UpdateRating updateRating;
    @InjectMocks
    private DeleteReviewUseCaseImpl deleteReviewUseCase;

    @Test
    void deleteReview() {
        Review review = Review.builder()
                .id("reviewID")
                .build();

        when(reviewIsValid.reviewInvalid("reviewID")).thenReturn(review);

        deleteReviewUseCase.deleteReview("reviewID");

        verify(reviewRepository).deleteById("reviewID");
    }
}