package lombelo.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Niklas Wünsche
 */
public interface UserAccountRepository extends CrudRepository <Account, Long> {
}
