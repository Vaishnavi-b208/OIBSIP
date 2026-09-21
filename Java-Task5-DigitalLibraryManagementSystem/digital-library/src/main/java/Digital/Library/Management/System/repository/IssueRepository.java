package Digital.Library.Management.System.repository;

import Digital.Library.Management.System.entity.Issue;
import Digital.Library.Management.System.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByUser(User user);

    List<Issue> findByStatus(String status);
}