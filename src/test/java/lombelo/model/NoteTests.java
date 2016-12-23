package lombelo.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Niklas Wünsche
 */
public class NoteTests {

    private Note note;

    @Before
    public void before() {
        String title = "title";
        String text = "text";
        String tags = "a b,c, d , e";

        ContentOfNote content = new ContentOfNote();
        content.setTitleOfNote(title);
        content.setTextOfNote(text);
        content.setTagsOfNote(tags);

        note = new Note(content);
    }

    @Test
    public void createdNoteRight() {
        assertThat(note.getDateOfCreation(), is(LocalDate.now()));
        assertThat(note.getTitle(), is("title"));
        assertThat(note.getTags(), is(Sets.newSet("a", "b", "c", "d", "e")));
        assertThat(note.getTagsAsString(), is("a, b, c, d, e"));
        assertThat(note.containsAllTags(""), is(true));
        assertThat(note.containsAllTags("e, c"), is(true));
        assertThat(note.containsAllTags("a, r"), is(false));
    }

    @Test
    public void createNoteWithRightDate() {
        Note newNote = new Note("", "", "");
        assertThat(newNote.getDateOfCreation(), is(LocalDate.now()));
    }

}