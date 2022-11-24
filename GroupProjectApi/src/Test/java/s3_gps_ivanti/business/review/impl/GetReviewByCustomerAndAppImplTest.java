package s3_gps_ivanti.business.review.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.dtoconvertor.ReviewDTOConverter;
import s3_gps_ivanti.business.validation.CustomerUsernameValidation;
import s3_gps_ivanti.dto.review.UpdateReviewDTO;
import s3_gps_ivanti.repository.ReviewRepository;
import s3_gps_ivanti.repository.entity.Review;
import s3_gps_ivanti.repository.entity.User;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetReviewByCustomerAndAppImplTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private CustomerUsernameValidation customerIsValid;
    @InjectMocks
    private GetReviewByCustomerAndAppUseCaseImpl getReviewByCustomerAndApp;

    @Test
    void getReviewByCustomerAndApp() {
        User user = User.builder()
                .username("customer")
                .build();
        Review review = Review.builder()
                .id("1")
                .description("description")
                .title("title")
                .rating(1)
                .build();

        when(customerIsValid.customerIsValid("customer")).thenReturn(user);
        when(reviewRepository.findByCustomerAndApplicationName(user, "app")).thenReturn(review);

        UpdateReviewDTO expected = ReviewDTOConverter.convertToDTOForUpdate(review);
        UpdateReviewDTO actual = getReviewByCustomerAndApp.getReviewByCustomerAndApp("customer", "app");


        assertEquals(expected, actual);

    }
}