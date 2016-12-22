package lombelo.controller;

import lombelo.model.ContentOfNote;
import lombelo.model.Note;
import lombelo.model.NoteRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Niklas Wünsche
 */
@Controller
public class SiteController {

    @Autowired NoteRepository notes;

    @RequestMapping("/")
    public String mapLandingPage() {
        return "landingPage";
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
    public String mapShowNotes(Model model) {
        model.addAttribute("notes", notes.findAll());
        return "showNotes";
    }

    @RequestMapping("/editNote/{Id}")
    public String mapEditNotes(@PathVariable Long Id, Model model) {
        model.addAttribute("note", notes.findOne(Id));
        return "editNote";
    }

    @RequestMapping("/editNote/finished/{Id}")
    public String mapEditFinisedNotes(@PathVariable Long Id, @ModelAttribute ContentOfNote content) {
        Note toEdit = notes.findOne(Id);

        toEdit.setTitle(content.getTitleOfNote());
        toEdit.setText(content.getTextOfNote());

        notes.save(toEdit);

        return "redirect:/showNotes";
    }

}
