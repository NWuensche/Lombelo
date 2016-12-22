package lombelo.controller;
import lombelo.AbstractionWebIntegrationTests;
import lombelo.model.Note;
import lombelo.model.NoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Niklas Wünsche
 */
public class SiteControllerWebIntegrationTests extends AbstractionWebIntegrationTests {

    @Autowired private NoteRepository notes;

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
    public void executeSaveNewNote() throws Exception{
        RequestBuilder serviceRequest = post("/addNote/created")
                .param("titleOfNote", "My Note")
                .param("contentOfNote", "BlaBlaBla");

        mvc.perform(serviceRequest)
                .andExpect(view().name("landingPage"));
    }

}
