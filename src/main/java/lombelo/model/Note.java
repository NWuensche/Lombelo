package lombelo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * @author Niklas Wünsche
 */
@Entity
@Getter
public class Note {

    @Id @GeneratedValue private Long Id;
    @Setter private String title;
    @Setter private String text;
    private LocalDate dateOfCreation;

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        dateOfCreation = LocalDate.now();
    }

    public Note(ContentOfNote content) {
        this(content.getTitleOfNote(), content.getTextOfNote());
    }

}
