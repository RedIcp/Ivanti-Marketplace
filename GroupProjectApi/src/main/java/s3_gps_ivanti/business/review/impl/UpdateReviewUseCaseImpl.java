package s3_gps_ivanti.business.review.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.dtoconvertor.ReplyDTOConverter;
import s3_gps_ivanti.business.exception.UnauthorizedDataAccessException;
import s3_gps_ivanti.business.review.UpdateReviewUseCase;
import s3_gps_ivanti.business.validation.ReviewIDValidation;
import s3_gps_ivanti.dto.review.CreateUpdateDeleteReplyDTO;
import s3_gps_ivanti.dto.review.UpdateReviewDTO;
import s3_gps_ivanti.repository.ReviewRepository;
import s3_gps_ivanti.repository.entity.Review;

import java.time.LocalDateTime;

@Service
@Primary
@RequiredArgsConstructor
public class UpdateReviewUseCaseImpl implements UpdateReviewUseCase {
    private final ReviewRepository reviewRepository;
    private final ReviewIDValidation reviewIsValid;
    private final UpdateRating updateRating;

    @Override
    public void updateReview(UpdateReviewDTO request) {
        Review review = reviewIsValid.reviewInvalid(request.getId());

        updateRating.subtractAppRating(review.getApplicationName(), review.getRating());

        review.setDescription(request.getDescription());
        review.setTitle(request.getTitle());
        review.setRating(request.getRating());
        review.setDate(LocalDateTime.now());

        updateRating.addAppRating(review.getApplicationName(), review.getRating());

        reviewRepository.save(review);
    }
    @Override
    public void replyAction(CreateUpdateDeleteReplyDTO request) {
        Review review = reviewIsValid.reviewInvalid(request.getId());

        review.setReply(ReplyDTOConverter.convertToEntity(request.getReply()));

        reviewRepository.save(review);
    }
}
