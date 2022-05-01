package lt.ivmov.yogaWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/welcome")
public class UserController {

    @GetMapping
    public String getLogInPage(){
        return "log-in";
    }
}
