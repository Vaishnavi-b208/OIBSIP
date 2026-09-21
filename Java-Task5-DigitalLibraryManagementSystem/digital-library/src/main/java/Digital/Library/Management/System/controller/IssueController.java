package Digital.Library.Management.System.controller;

import Digital.Library.Management.System.entity.Book;
import Digital.Library.Management.System.entity.Issue;
import Digital.Library.Management.System.entity.User;
import Digital.Library.Management.System.service.BookService;
import Digital.Library.Management.System.service.FineService;
import Digital.Library.Management.System.service.IssueService;
import Digital.Library.Management.System.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IssueController {

    private final IssueService issueService;
    private final BookService bookService;
    private final UserService userService;
    private final FineService fineService;

    public IssueController(
            IssueService issueService,
            BookService bookService,
            UserService userService,
            FineService fineService) {

        this.issueService = issueService;
        this.bookService = bookService;
        this.userService = userService;
        this.fineService = fineService;
    }

    /*
     * USER - MY BOOKS
     */
    @GetMapping("/user/my-books")
    public String myBooks(
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
                "issues",
                issueService.getIssuesByUser(user)
        );

        model.addAttribute("user", user);

        return "my-books";
    }

    /*
     * USER - ISSUE BOOK
     */
    @PostMapping("/user/issue/{bookId}")
    public String issueBook(
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

            issueService.issueBook(user, book);

            return "redirect:/user/my-books";

        } catch (IllegalStateException | IllegalArgumentException e) {

            model.addAttribute("error", e.getMessage());
            model.addAttribute(
                    "books",
                    bookService.getAllBooks()
            );

            return "books";
        }
    }

    /*
     * USER - RETURN BOOK
     *
     * The book is returned first.
     * Then the fine is calculated automatically.
     */
    @PostMapping("/user/return/{issueId}")
    public String returnBook(
            @PathVariable Long issueId,
            HttpSession session,
            Model model) {

        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        try {

            /*
             * Get the issue before returning it.
             */
            issueService.returnBook(issueId);

Issue returnedIssue = issueService.getIssueById(issueId)
        .orElseThrow(() -> new IllegalArgumentException("Issue not found."));

fineService.calculateFine(returnedIssue);

            return "redirect:/user/my-books";

        } catch (IllegalArgumentException e) {

            model.addAttribute("error", e.getMessage());

            return "redirect:/user/my-books";
        }
    }

    /*
     * ADMIN - VIEW ALL ISSUED BOOKS
     */
    @GetMapping("/admin/issued-books")
    public String issuedBooks(Model model) {

        model.addAttribute(
                "issues",
                issueService.getAllIssues()
        );

        return "issued-books";
    }
}