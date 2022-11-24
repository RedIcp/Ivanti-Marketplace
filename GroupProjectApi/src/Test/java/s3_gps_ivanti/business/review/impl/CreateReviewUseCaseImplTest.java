package s3_gps_ivanti.business.review.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import s3_gps_ivanti.business.dtoconvertor.ReviewDTOConverter;
import s3_gps_ivanti.business.exception.ApplicationNotFoundException;
import s3_gps_ivanti.business.exception.CustomerNotFoundException;
import s3_gps_ivanti.business.exception.ReviewByCustomerAlreadyExistException;
import s3_gps_ivanti.business.validation.ApplicationNameValidation;
import s3_gps_ivanti.business.validation.CustomerUsernameValidation;
import s3_gps_ivanti.dto.review.CreateReviewRequestDTO;
import s3_gps_ivanti.dto.review.CreateReviewResponseDTO;
import s3_gps_ivanti.repository.ReviewRepository;
import s3_gps_ivanti.repository.entity.Application;
import s3_gps_ivanti.repository.entity.Review;
import s3_gps_ivanti.repository.entity.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateReviewUseCaseImplTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UpdateRating updateRating;
    @Mock
    private ApplicationNameValidation applicationIsValid;
    @Mock
    private CustomerUsernameValidation customerIsValid;
    @InjectMocks
    private CreateReviewUseCaseImpl createReviewUseCase;

    @Test
    void createReviewAlreadyExist() {
        User user = User.builder()
                .id("id")
                .build();

        CreateReviewRequestDTO requestDTO = CreateReviewRequestDTO.builder()
                .customerName("name")
                .applicationName("applicationID")
                .rating(5)
                .title("title")
                .description("description")
                .build();

        when(applicationIsValid.applicationIsValid("applicationID")).thenReturn(new Application());
        when(customerIsValid.customerIsValid("name")).thenReturn(user);
        when(reviewRepository.existsByCustomerAndAndApplicationName(user, "applicationID")).thenReturn(true);

        ReviewByCustomerAlreadyExistException exception = assertThrows(ReviewByCustomerAlreadyExistException.class, () -> createReviewUseCase.createReview(requestDTO));

        assertEquals("REVIEW_BY_CUSTOMER_ALREADY_EXIST", exception.getReason());
    }
}