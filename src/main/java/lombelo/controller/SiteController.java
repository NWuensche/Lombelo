package lombelo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Niklas Wünsche
 */
@Controller
public class SiteController {

    @RequestMapping("/")
    public String mapLandingPage() {
        return "landingPage";
    }

}
