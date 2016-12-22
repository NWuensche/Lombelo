package lombelo.controller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Niklas Wünsche
 */

@RunWith(SpringRunner.class)
@WebMvcTest(SiteController.class)
public class SiteControllerWebIntegrationTests {

    @Autowired private MockMvc mvc;

    @Test
    public void testLandingPageMapper() throws Exception {
        RequestBuilder serviceRequest = post("/");

        mvc.perform(serviceRequest)
                .andExpect(view().name("landingPage"));
    }

    @Test
    public void testAddNoteMapper() throws Exception {
        RequestBuilder serviceRequest = post("/addNote");

        mvc.perform(serviceRequest)
                .andExpect(model().attributeExists("content"))
                .andExpect(view().name("addNote"));
    }

}
