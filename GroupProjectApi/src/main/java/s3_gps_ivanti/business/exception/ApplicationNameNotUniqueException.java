package s3_gps_ivanti.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApplicationNameNotUniqueException extends ResponseStatusException {
    public ApplicationNameNotUniqueException() {super(HttpStatus.BAD_REQUEST, "APPLICATION_NAME_NOT_UNIQUE");
    }
}