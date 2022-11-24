package s3_gps_ivanti.business.review;

import s3_gps_ivanti.dto.review.UpdateReviewDTO;

public interface GetReviewByCustomerAndAppUseCase {
    UpdateReviewDTO getReviewByCustomerAndApp(String customer, String application);
}
