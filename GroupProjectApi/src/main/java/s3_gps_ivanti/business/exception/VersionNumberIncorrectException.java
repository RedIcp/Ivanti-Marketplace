package s3_gps_ivanti.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VersionNumberIncorrectException extends ResponseStatusException {
    public VersionNumberIncorrectException() {super(HttpStatus.BAD_REQUEST, "VERSION_NUMBER_INCORRECT");
    }
}
