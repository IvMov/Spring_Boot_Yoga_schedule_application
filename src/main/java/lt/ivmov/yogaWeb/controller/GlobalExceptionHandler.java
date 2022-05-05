package lt.ivmov.yogaWeb.controller;

import lombok.extern.slf4j.Slf4j;
import lt.ivmov.yogaWeb.exception.EventNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public String handleEvetnNotFoundException() {
        log.error("Looking for event which does not exist");
        return "redirect:/schedule";
    }
}
