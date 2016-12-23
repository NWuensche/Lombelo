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

        notes.save(toEdit);

        return "redirect:/showNotes";
    }

    @RequestMapping("/removeNote/{id}")
    public String mapEditFinisedNotes(@PathVariable Long id) {
        Note toRemove = notes.findOne(id);
        notes.delete(toRemove);

        return "redirect:/showNotes";
    }

}
