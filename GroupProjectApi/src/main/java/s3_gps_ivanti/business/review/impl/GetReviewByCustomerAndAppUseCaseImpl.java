package s3_gps_ivanti.business.review.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import s3_gps_ivanti.business.dtoconvertor.ReviewDTOConverter;
import s3_gps_ivanti.business.review.GetReviewByCustomerAndAppUseCase;
import s3_gps_ivanti.dto.review.UpdateReviewDTO;
import s3_gps_ivanti.repository.ReviewRepository;
import s3_gps_ivanti.repository.UserRepository;
import s3_gps_ivanti.repository.entity.Review;
import s3_gps_ivanti.repository.entity.User;

@Service
@Primary
@RequiredArgsConstructor
public class GetReviewByCustomerAndAppUseCaseImpl implements GetReviewByCustomerAndAppUseCase {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    public UpdateReviewDTO getReviewByCustomerAndApp(String customer, String application) {
        User user = userRepository.findUserByUsername(customer);
        if(user != null){
            Review review = reviewRepository.findByCustomerAndApplicationName(user, application);
            return ReviewDTOConverter.convertToDTOForUpdate(review);
        }
        else return null;
    }
}
