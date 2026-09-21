package Digital.Library.Management.System.controller;

import Digital.Library.Management.System.entity.Book;
import Digital.Library.Management.System.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // View all books
    @GetMapping("/books")
    public String viewBooks(
            @RequestParam(required = false) String search,
            Model model) {

        if (search != null && !search.isBlank()) {
            model.addAttribute("books",
                    bookService.searchByTitle(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("books",
                    bookService.getAllBooks());
        }

        return "books";
    }

    // View books by category
    @GetMapping("/books/category/{category}")
    public String booksByCategory(
            @PathVariable String category,
            Model model) {

        model.addAttribute(
                "books",
                bookService.searchByCategory(category)
        );

        model.addAttribute("category", category);

        return "books";
    }

    // Add book page
    @GetMapping("/admin/books/add")
    public String addBookPage(Model model) {

        model.addAttribute("book", new Book());

        return "add-book";
    }

    // Save new or edited book
    @PostMapping("/admin/books/save")
    public String saveBook(@ModelAttribute Book book) {

        // New book
        if (book.getId() == null) {

            // Initially all copies are available
            book.setAvailableQuantity(book.getQuantity());

        } else {

            // Make sure available quantity is valid
            if (book.getAvailableQuantity() < 0) {
                book.setAvailableQuantity(0);
            }

            if (book.getAvailableQuantity() > book.getQuantity()) {
                book.setAvailableQuantity(book.getQuantity());
            }
        }

        bookService.saveBook(book);

        return "redirect:/admin/books";
    }

    // Edit book page
    @GetMapping("/admin/books/edit/{id}")
    public String editBookPage(
            @PathVariable Long id,
            Model model) {

        Book book = bookService.getBookById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Book not found."));

        model.addAttribute("book", book);

        return "edit-book";
    }

    // Delete book
    @GetMapping("/admin/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);

        return "redirect:/admin/books";
    }
}