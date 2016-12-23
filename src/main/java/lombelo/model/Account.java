package lombelo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Niklas Wünsche
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, onConstructor = @__({@Deprecated}))
public class Account {

    @GeneratedValue @Id private Long id;
    @Setter private String userName;
    @Setter private String password;

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

}
