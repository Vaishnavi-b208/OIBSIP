package Digital.Library.Management.System.service;

import Digital.Library.Management.System.entity.Book;
import Digital.Library.Management.System.entity.Reservation;
import Digital.Library.Management.System.entity.User;
import Digital.Library.Management.System.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation reserveBook(User user, Book book) {

        Reservation reservation = new Reservation();

        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDate.now());
        reservation.setStatus("RESERVED");

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUser(User user) {
        return reservationRepository.findByUser(user);
    }

    public List<Reservation> getReservationsByBook(Book book) {
        return reservationRepository.findByBook(book);
    }

    public List<Reservation> getActiveReservations() {
        return reservationRepository.findByStatus("RESERVED");
    }

    public Reservation cancelReservation(Long id) {

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Reservation not found."));

        reservation.setStatus("CANCELLED");

        return reservationRepository.save(reservation);
    }
}