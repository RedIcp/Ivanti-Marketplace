package s3_gps_ivanti.business.review.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.dtoconvertor.ReplyDTOConverter;
import s3_gps_ivanti.business.validation.ReviewIDValidation;
import s3_gps_ivanti.dto.response.ReplyDTO;
import s3_gps_ivanti.dto.review.CreateUpdateDeleteReplyDTO;
import s3_gps_ivanti.dto.review.UpdateReviewDTO;
import s3_gps_ivanti.repository.ReviewRepository;
import s3_gps_ivanti.repository.entity.Review;
import s3_gps_ivanti.repository.entity.User;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateReviewUseCaseImplTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewIDValidation reviewIsValid;
    @Mock
    private UpdateRating updateRating;
    @InjectMocks
    private UpdateReviewUseCaseImpl updateReviewUseCase;

    @Test
    void addingValidReplyToReview() {
        Review oldReview = Review.builder()
                .id("id")
                .rating(1)
                .description("oldDescription")
                .title("oldTile")
                .build();

        when(reviewIsValid.reviewInvalid("id")).thenReturn(oldReview);

        ReplyDTO replyDTO = ReplyDTO.builder()
                .title("title")
                .description("description")
                .build();
        CreateUpdateDeleteReplyDTO request = CreateUpdateDeleteReplyDTO.builder()
                .id("id")
                .reply(replyDTO)
                .build();

        Review newReview = Review.builder()
                .id("id")
                .rating(1)
                .description("oldDescription")
                .title("oldTile")
                .reply(ReplyDTOConverter.convertToEntity(replyDTO))
                .build();

        updateReviewUseCase.replyAction(request);

        verify(reviewRepository).save(newReview);
    }
}