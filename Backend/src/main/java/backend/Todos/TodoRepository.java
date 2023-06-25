package backend.Todos;
import backend.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 *
 */

public interface TodoRepository extends JpaRepository<Todo, Long>{

    Todo findById(int id);

    Todo findByTitle(String title);
    Todo findByUser(User user);

    List<Todo> findListByUser(User user);
    @Transactional
    void deleteById(int id);

}

