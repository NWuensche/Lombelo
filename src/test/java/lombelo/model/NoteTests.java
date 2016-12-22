package lombelo.model;

import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Niklas Wünsche
 */
public class NoteTests {

    @Test
    public void createNoteWithRightDate() {
        String title = "title";
        String text = "text";
        Note note = new Note(title, text);
        assertThat(note.getDateOfCreation(), is(LocalDate.now()));

        ContentOfNote content = new ContentOfNote();
        content.setTitleOfNote(title);
        content.setTextOfNote(text);
        Note note2 = new Note(content);
        assertThat(note2.getDateOfCreation(), is(LocalDate.now()));
        assertThat(note2.getTitle(), is(title));
        assertThat(note2.getText(), is(text));
    }

}