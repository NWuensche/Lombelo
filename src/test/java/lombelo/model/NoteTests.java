package lombelo.model;

import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

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
        String tags = "a b,c, d , e";
        Note note = new Note(title, text, tags);
        assertThat(note.getDateOfCreation(), is(LocalDate.now()));

        ContentOfNote content = new ContentOfNote();
        content.setTitleOfNote(title);
        content.setTextOfNote(text);
        content.setTagsOfNote(tags);

        Note note2 = new Note(content);

        assertThat(note2.getDateOfCreation(), is(LocalDate.now()));
        assertThat(note2.getTitle(), is(title));
        assertThat(note2.getTags(), is(Sets.newSet("a", "b", "c", "d", "e")));
        assertThat(note2.getTagsAsString(), is("a, b, c, d, e"));
    }

}