package s3_gps_ivanti.business.validation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.exception.InvalidReviewException;
import s3_gps_ivanti.repository.ReviewRepository;
import s3_gps_ivanti.repository.entity.Review;

@Service
@AllArgsConstructor
public class ReviewIDValidation {
    private ReviewRepository reviewRepository;

    public Review reviewInvalid(String id){
        if(!reviewRepository.existsById(id)){
            throw new InvalidReviewException("INVALID_REVIEW");
        }
        else
            return reviewRepository.findById(id).orElse(null);
    }
}
