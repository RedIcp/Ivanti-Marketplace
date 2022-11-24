package s3_gps_ivanti.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApplicationHasNoVersionException extends ResponseStatusException {
    public ApplicationHasNoVersionException() {super(HttpStatus.BAD_REQUEST, "APPLICATION_HAS_NO_VERSIONS");
    }
}
