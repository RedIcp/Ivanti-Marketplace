package s3_gps_ivanti.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRatingException extends ResponseStatusException {
    public InvalidRatingException() {
        super(HttpStatus.BAD_REQUEST, "RATING_INVALID");
    }
}