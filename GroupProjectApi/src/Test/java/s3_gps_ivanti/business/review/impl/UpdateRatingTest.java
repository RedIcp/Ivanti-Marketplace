package s3_gps_ivanti.business.review.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s3_gps_ivanti.business.exception.InvalidRatingException;
import s3_gps_ivanti.repository.ApplicationRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateRatingTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @InjectMocks
    private UpdateRating updateRating;

    @Test
    void appRatingAddShouldThrowInvalidRatingExceptionWhenRatingMoreThan5() {
        assertThrows(InvalidRatingException.class, ()->updateRating.addAppRating("app", 6));
    }

    @Test
    void subtractRatingAddShouldThrowInvalidRatingExceptionWhenRatingMoreThan5() {
        assertThrows(InvalidRatingException.class, ()->updateRating.subtractAppRating("app", 6));
    }
}