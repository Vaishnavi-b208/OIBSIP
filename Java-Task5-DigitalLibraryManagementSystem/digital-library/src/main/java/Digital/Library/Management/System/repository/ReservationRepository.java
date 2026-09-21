package Digital.Library.Management.System.repository;

import Digital.Library.Management.System.entity.Reservation;
import Digital.Library.Management.System.entity.User;
import Digital.Library.Management.System.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);

    List<Reservation> findByBook(Book book);

    List<Reservation> findByStatus(String status);
}