package s3_gps_ivanti.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CantCreateApplicationException extends ResponseStatusException {
    public CantCreateApplicationException() {super(HttpStatus.BAD_REQUEST, "DATA_INCORRECT_APPLICATION_CAN_NOT_BE_CREATED");}
}
