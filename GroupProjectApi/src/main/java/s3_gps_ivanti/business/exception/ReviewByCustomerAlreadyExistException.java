package s3_gps_ivanti.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ReviewByCustomerAlreadyExistException extends ResponseStatusException {
    public ReviewByCustomerAlreadyExistException() {
        super(HttpStatus.BAD_REQUEST, "REVIEW_BY_CUSTOMER_ALREADY_EXIST");
    }
}