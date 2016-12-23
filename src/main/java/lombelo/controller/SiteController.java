package lombelo.controller;

import lombelo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Niklas Wünsche
 */
@Controller
public class SiteController {

    @Autowired NoteRepository notes;
    @Autowired AccountRepository accounts;

    @RequestMapping("/")
    public String mapLandingPage() {
        return "landingPage";
    }

    @RequestMapping("/login")
    public String mapLoginPage() {
        return "login";
    }

    @RequestMapping("/addNote")
    public String mapAddNote(Model model) {
        model.addAttribute("content", new ContentOfNote());
        return "addNote";
    }

    @RequestMapping("/addNote/created")
    public String saveNewNote(@ModelAttribute ContentOfNote content) {
        notes.save(new Note(content));
        return "landingPage";
    }

    @RequestMapping("/showNotes")
    public String mapShowNotes(Model model, @ModelAttribute(value="searchTags") Optional<String> searchTags) {
        model.addAttribute("notes", notes.findAll());
        model.addAttribute("searchTags", searchTags.orElse(""));
        return "showNotes";
    }

    @RequestMapping("/editNote/{id}")
    public String mapEditNotes(@PathVariable Long id, Model model) {
        model.addAttribute("note", notes.findOne(id));
        return "editNote";
    }

    @RequestMapping("/editNote/finished/{id}")
    public String mapEditFinisedNotes(@PathVariable Long id, @ModelAttribute ContentOfNote content) {
        Note toEdit = notes.findOne(id);

        toEdit.setTitle(content.getTitleOfNote());
        toEdit.setText(content.getTextOfNote());
        toEdit.setTags(content.getTagsOfNote());
        toEdit.setLastChanged(LocalDate.now());

        notes.save(toEdit);

        return "redirect:/showNotes";
    }

    @RequestMapping("/removeNote/{id}")
    public String mapEditFinisedNotes(@PathVariable Long id) {
        Note toRemove = notes.findOne(id);
        notes.delete(toRemove);

        return "redirect:/showNotes";
    }

    @RequestMapping("/settings")
    public String mapSettings(Model model) {
        model.addAttribute("userName", "");
        model.addAttribute("password", "");

        return "settings";
    }

    @RequestMapping("/settings/finished")
    public String mapSettings(@ModelAttribute(value="userName") String userName,
                              @ModelAttribute(value="password") String password) {

        accounts.deleteAll();

        Account newUser = new Account(userName, password);
        accounts.save(newUser);

        return "landingPage";
    }

}
