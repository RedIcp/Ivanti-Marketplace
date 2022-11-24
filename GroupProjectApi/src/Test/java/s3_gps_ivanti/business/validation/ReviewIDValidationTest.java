package s3_gps_ivanti.business.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.exception.InvalidReviewException;
import s3_gps_ivanti.repository.ReviewRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewIDValidationTest {

    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private ReviewIDValidation reviewIDValidation;

    @Test
    void reviewInvalid() {

        when(reviewRepository.existsById("id"))
                .thenReturn(true);

        reviewIDValidation.reviewInvalid("id");

        verify(reviewRepository).existsById("id");
    }

    @Test
    void reviewInvalid_DosntExist() {

        when(reviewRepository.existsById("id"))
                .thenReturn(false);

        assertThrows(InvalidReviewException.class,() -> reviewIDValidation.reviewInvalid("id"));

        verify(reviewRepository).existsById("id");
    }
}