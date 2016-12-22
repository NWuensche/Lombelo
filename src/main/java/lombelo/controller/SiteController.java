package lombelo.controller;

import lombelo.model.ContentOfNote;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
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

    @RequestMapping("/addNote")
    public String mapAddNote(Model model) {
        model.addAttribute("content", new ContentOfNote());
        return "addNote";
    }
}