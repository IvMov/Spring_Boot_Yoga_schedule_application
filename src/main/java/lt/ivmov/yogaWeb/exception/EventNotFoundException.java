package lt.ivmov.yogaWeb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Event did not exist")
public class EventNotFoundException extends RuntimeException {

}
