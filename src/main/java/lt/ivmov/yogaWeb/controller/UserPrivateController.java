package lt.ivmov.yogaWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/private/user")
public class UserPrivateController {

    @GetMapping
    public String getUserPage(){
        return "blank";
    }
}
