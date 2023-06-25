package backend.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 *
 */


public interface GroupRepository extends JpaRepository<Group, Long>{

    Group findById(int id);

    Group findByGroupname(String groupname);

    @Transactional
    void deleteById(int id);

}

