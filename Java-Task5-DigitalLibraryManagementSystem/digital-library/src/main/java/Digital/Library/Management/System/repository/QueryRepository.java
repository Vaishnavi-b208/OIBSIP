package Digital.Library.Management.System.repository;

import Digital.Library.Management.System.entity.Query;
import Digital.Library.Management.System.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryRepository extends JpaRepository<Query, Long> {

    List<Query> findByUser(User user);

    List<Query> findByStatus(String status);
}