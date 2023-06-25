package backend.Stacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface StackRepository extends JpaRepository<Stack, Long>{

    Stack findById(int id);

    Stack findByTitle(String title);

    @Transactional
    void deleteById(int id);



}