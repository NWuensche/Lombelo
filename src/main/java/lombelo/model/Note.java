package lombelo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Niklas Wünsche
 */
@Entity
@Getter
public class Note {

    @Id @GeneratedValue private long Id;
    @Setter private String title;
    @Setter private String text;
    private LocalDateTime dateOfCreation;

    public Note(String title, String text) {
        this.title = title;
        this.text = text;
        dateOfCreation = LocalDateTime.now();
    }

}
