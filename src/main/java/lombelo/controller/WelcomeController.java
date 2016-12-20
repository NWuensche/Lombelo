package lombelo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Niklas Wünsche
 */
@Controller
public class WelcomeController {

    @RequestMapping("/welcome")
    public String mapWelcome() {
        return "welcome";
    }

}
