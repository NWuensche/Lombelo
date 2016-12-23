package lombelo.controller;
import lombelo.AbstractionWebIntegrationTests;
import lombelo.model.Account;
import lombelo.model.AccountRepository;
import lombelo.model.Note;
import lombelo.model.NoteRepository;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Niklas Wünsche
 */
public class SiteControllerWebIntegrationTests extends AbstractionWebIntegrationTests {

    @Autowired private NoteRepository notes;
    @Autowired private AccountRepository accounts;

    @Test
    public void executeLandingPageMapper() throws Exception {
        RequestBuilder serviceRequest = post("/");

        mvc.perform(serviceRequest)
                .andExpect(view().name("landingPage"));
    }

    @Test
    public void executeAddNoteMapper() throws Exception {
        RequestBuilder serviceRequest = post("/addNote");

        mvc.perform(serviceRequest)
                .andExpect(model().attributeExists("content"))
                .andExpect(view().name("addNote"));
    }

    @Test
    public void executeSaveNewNote() throws Exception {
        String noteTitle = "My Note";
        String textTitle = "BlaBlaBla";
        RequestBuilder serviceRequest = post("/addNote/created")
                .param("titleOfNote", noteTitle)
                .param("textOfNote", textTitle)
                .param("tagsOfNote", "");

        mvc.perform(serviceRequest)
                .andExpect(view().name("landingPage"));

        Optional<Note> savedNote = StreamSupport
                .stream(notes.findAll().spliterator(), false)
                .filter(note -> note.getTitle().equals(noteTitle))
                .findAny();

        assertThat(savedNote.isPresent(), is(true));
        assertThat(savedNote.get().getText(), is(textTitle));
    }

    @Test
    public void executeShowAllNotes() throws Exception {
        RequestBuilder serviceRequest = post("/showNotes");
        mvc.perform(serviceRequest)
            .andExpect(model().attribute("notes", notes.findAll()))
            .andExpect(view().name("showNotes"));
    }

    @Test
    public void executeEditNote() throws Exception {
        Note note = new Note("title", "text", "");
        notes.save(note);

        RequestBuilder serviceRequest = get("/editNote/" + note.getId());
        mvc.perform(serviceRequest)
                .andExpect(view().name("editNote"));
    }

    @Test
    public void executeFinishEditNote() throws Exception {
        Note note = new Note("title", "text", "");
        notes.save(note);

        RequestBuilder serviceRequest = post("/editNote/finished/" + note.getId())
                .param("titleOfNote", "newTitle")
                .param("textOfNote", "newText")
                .param("tagsOfNote", "a b,c, d , e");

        mvc.perform(serviceRequest)
                .andExpect(status().is3xxRedirection());

        Optional<Note> editedNote = StreamSupport
                .stream(notes.findAll().spliterator(), false)
                .filter(savedNote -> savedNote.getTitle().equals("newTitle"))
                .findAny();

        assertThat(editedNote.isPresent(), is(true));
        assertThat(editedNote.get().getText(), is("newText"));
        assertThat(editedNote.get().getTags(), is(Sets.newSet("a", "b", "c", "d", "e")));
    }

    @Test
    public void executeRemoveNote() throws Exception {
        Note toRemove = new Note("remove", "remove", "");
        notes.save(toRemove);

        RequestBuilder serviceRequest = post("/removeNote/" + toRemove.getId());

        mvc.perform(serviceRequest)
                .andExpect(status().is3xxRedirection());

        Optional<Note> editedNote = StreamSupport
                .stream(notes.findAll().spliterator(), false)
                .filter(savedNote -> savedNote.getTitle().equals("remove"))
                .findAny();

        assertThat(editedNote.isPresent(), is(false));
    }

    @Test
    public void executeSearchByTags() throws Exception {
        Note note = new Note("title", "text", "findT1, b");
        Note note2 = new Note("title", "text", "findT2, b");
        Note note3 = new Note("title", "text", "a, b");

        notes.save(Arrays.asList(note, note2, note3));

        RequestBuilder serviceRequest = post("/showNotes")
                .param("searchTags", "findT1, findT2");

        mvc.perform(serviceRequest)
                .andExpect(view().name("showNotes"));
    }

    @Test
    public void executeSettings() throws Exception {
        RequestBuilder serviceRequest = post("/settings");

        mvc.perform(serviceRequest)
                .andExpect(model().attributeExists("userName"))
                .andExpect(model().attributeExists("password"))
                .andExpect(view().name("settings"));
    }

    @Test
    public void changeUser() throws Exception {
        RequestBuilder serviceRequest = post("/settings/finished")
                .param("userName", "name")
                .param("password", "pass");

        mvc.perform(serviceRequest)
                .andExpect(view().name("landingPage"));

        Account userAccount = StreamSupport.stream(accounts.findAll().spliterator(), false).findFirst().get();

        assertThat(userAccount.getUserName(), is("name"));
        assertThat(userAccount.getPassword(), is("pass"));

        // Change again

        RequestBuilder serviceRequest2 = post("/settings/finished")
                .param("userName", "name2")
                .param("password", "pass2");

        mvc.perform(serviceRequest2)
                .andExpect(view().name("landingPage"));

        assertThat(accounts.count(), is(1l));
        Account userAccount2 = StreamSupport.stream(accounts.findAll().spliterator(), false).findFirst().get();

        assertThat(userAccount2.getUserName(), is("name2"));
        assertThat(userAccount2.getPassword(), is("pass2"));
    }

}
