package lombelo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * @author Niklas Wünsche
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, onConstructor = @__({@Deprecated}))
public class Note {

    @Id @GeneratedValue private Long id;
    @Setter private String title;
    @Setter private String text;
    private HashSet<String> tags;
    @Setter private LocalDate lastChanged;

    public Note(String title, String text, String tags) {
        this.title = title;
        this.text = text;
        this.tags = splitTags(tags);
        lastChanged = LocalDate.now();
    }

    public Note(ContentOfNote content) {
        this(content.getTitleOfNote(), content.getTextOfNote(), content.getTagsOfNote());
    }

    /**
     * @param tags gap between words can be " "; ","; ", " ," or " , "
     * @return
     */
    public static HashSet<String> splitTags(String tags) {
        return (HashSet) Arrays.stream(tags.split("((\\s,*\\s*)|(\\s*,*\\s)|(\\s*,\\s*))")).collect(Collectors.toSet());
    }

    public void setTags(String tags) {
        this.tags = splitTags(tags);
    }

    public String getTagsAsString() {
        return tags.stream().reduce((a, b) -> a.concat(", ").concat(b)).orElse("");
    }

    public boolean containsAllTags(String searchTags) {
        if(StringUtils.isBlank(searchTags)) {
            return true;
        }

        HashSet<String> givenTagsSet = splitTags(searchTags);
        return tags.containsAll(givenTagsSet);
    }

}
