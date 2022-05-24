package lt.ivmov.yogaWeb.controller;

import lombok.extern.slf4j.Slf4j;
import lt.ivmov.yogaWeb.exception.EventNotFoundException;
import lt.ivmov.yogaWeb.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public String handleEventNotFoundException() {
        log.error("Searching for event which does not exist. Try to get not existing event page");
        return "redirect:/public/schedule";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException() {
        log.error("Searching for user which does not exist. Try to get not existing user page");
        return "redirect:/public/schedule";
    }
}
