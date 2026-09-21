package Digital.Library.Management.System.repository;

import Digital.Library.Management.System.entity.Fine;
import Digital.Library.Management.System.entity.Issue;
import Digital.Library.Management.System.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FineRepository extends JpaRepository<Fine, Long> {

    List<Fine> findByUser(User user);

    List<Fine> findByPaid(boolean paid);

    Optional<Fine> findByIssue(Issue issue);
}