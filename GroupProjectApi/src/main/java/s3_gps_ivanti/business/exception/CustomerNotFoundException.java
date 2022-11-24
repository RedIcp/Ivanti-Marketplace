package s3_gps_ivanti.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerNotFoundException  extends ResponseStatusException {
    public CustomerNotFoundException() {super(HttpStatus.BAD_REQUEST, "USER_NOT_FOUND");
    }
}
