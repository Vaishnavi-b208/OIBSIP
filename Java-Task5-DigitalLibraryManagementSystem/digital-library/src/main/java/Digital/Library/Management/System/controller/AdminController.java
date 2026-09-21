package Digital.Library.Management.System.controller;

import Digital.Library.Management.System.service.BookService;
import Digital.Library.Management.System.service.FineService;
import Digital.Library.Management.System.service.IssueService;
import Digital.Library.Management.System.service.QueryService;
import Digital.Library.Management.System.service.ReservationService;
import Digital.Library.Management.System.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private final BookService bookService;
    private final UserService userService;
    private final IssueService issueService;
    private final FineService fineService;
    private final QueryService queryService;
    private final ReservationService reservationService;

    public AdminController(BookService bookService,
                           UserService userService,
                           IssueService issueService,
                           FineService fineService,
                           QueryService queryService,
                           ReservationService reservationService) {

        this.bookService = bookService;
        this.userService = userService;
        this.issueService = issueService;
        this.fineService = fineService;
        this.queryService = queryService;
        this.reservationService = reservationService;
    }

    // ==============================
    // ADMIN DASHBOARD
    // ==============================

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

        model.addAttribute(
                "totalBooks",
                bookService.getAllBooks().size()
        );

        model.addAttribute(
                "totalMembers",
                userService.getAllUsers().size()
        );

        model.addAttribute(
                "issuedBooks",
                issueService.getIssuedBooks().size()
        );

        model.addAttribute(
                "unpaidFines",
                fineService.getUnpaidFines().size()
        );

        model.addAttribute(
                "pendingQueries",
                queryService.getPendingQueries().size()
        );

        model.addAttribute(
                "reservations",
                reservationService.getActiveReservations().size()
        );

        return "admin-dashboard";
    }

    // ==============================
    // MANAGE BOOKS
    // ==============================

    @GetMapping("/admin/books")
    public String manageBooks(Model model) {

        model.addAttribute(
                "books",
                bookService.getAllBooks()
        );

        return "manage-books";
    }

    // ==============================
    // MANAGE MEMBERS
    // ==============================

    @GetMapping("/admin/members")
    public String members(Model model) {

        model.addAttribute(
                "members",
                userService.getAllUsers()
        );

        return "members";
    }

    // ==============================
    // VIEW FINES
    // ==============================

    @GetMapping("/admin/fines")
    public String fines(Model model) {

        model.addAttribute(
                "fines",
                fineService.getAllFines()
        );

        return "fines";
    }

    // ==============================
    // VIEW QUERIES
    // ==============================

    @GetMapping("/admin/queries")
    public String queries(Model model) {

        model.addAttribute(
                "queries",
                queryService.getAllQueries()
        );

        return "queries";
    }

    // ==============================
    // VIEW RESERVATIONS
    // ==============================

    @GetMapping("/admin/reservations")
    public String reservations(Model model) {

        model.addAttribute(
                "reservations",
                reservationService.getAllReservations()
        );

        return "reservations";
    }
}