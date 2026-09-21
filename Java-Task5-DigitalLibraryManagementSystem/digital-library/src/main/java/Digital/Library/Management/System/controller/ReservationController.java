package Digital.Library.Management.System.controller;

import Digital.Library.Management.System.entity.Book;
import Digital.Library.Management.System.entity.Reservation;
import Digital.Library.Management.System.entity.User;
import Digital.Library.Management.System.service.BookService;
import Digital.Library.Management.System.service.ReservationService;
import Digital.Library.Management.System.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final BookService bookService;
    private final UserService userService;

    public ReservationController(
            ReservationService reservationService,
            BookService bookService,
            UserService userService) {

        this.reservationService = reservationService;
        this.bookService = bookService;
        this.userService = userService;
    }

    // View user's reservations
    @GetMapping("/user/reservations")
    public String myReservations(
            HttpSession session,
            Model model) {

        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found."));

        model.addAttribute(
                "reservations",
                reservationService.getReservationsByUser(user)
        );

        model.addAttribute("user", user);

        return "reservations";
    }

    // Reserve an unavailable book
    @PostMapping("/user/reserve/{bookId}")
    public String reserveBook(
            @PathVariable Long bookId,
            HttpSession session,
            Model model) {

        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        try {

            User user = userService.findByEmail(email)
                    .orElseThrow(() ->
                            new IllegalArgumentException("User not found."));

            Book book = bookService.getBookById(bookId)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Book not found."));

            reservationService.reserveBook(user, book);

            return "redirect:/user/reservations";

        } catch (IllegalArgumentException | IllegalStateException e) {

            model.addAttribute("error", e.getMessage());
            model.addAttribute(
                    "books",
                    bookService.getAllBooks()
            );

            return "books";
        }
    }

    // Cancel reservation
    @PostMapping("/user/reservation/cancel/{id}")
    public String cancelReservation(
            @PathVariable Long id,
            HttpSession session) {

        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found."));

        reservationService.cancelReservation(id);

        return "redirect:/user/reservations";
    }
}