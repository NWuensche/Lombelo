package lombelo.model;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Niklas Wünsche
 */
public interface NoteRepository extends CrudRepository<Note, Long> {
}
