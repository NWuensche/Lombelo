package lombelo.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Niklas Wünsche
 */
public interface AccountRepository extends CrudRepository <Account, Long> {
}
