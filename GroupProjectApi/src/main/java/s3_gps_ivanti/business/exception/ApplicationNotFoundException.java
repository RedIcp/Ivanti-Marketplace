package s3_gps_ivanti.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApplicationNotFoundException extends ResponseStatusException {
    public ApplicationNotFoundException() {super(HttpStatus.BAD_REQUEST, "APPLICATION_NOT_FOUND");
    }
}