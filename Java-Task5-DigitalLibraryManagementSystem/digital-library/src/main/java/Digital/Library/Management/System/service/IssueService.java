package Digital.Library.Management.System.service;

import Digital.Library.Management.System.entity.Book;
import Digital.Library.Management.System.entity.Issue;
import Digital.Library.Management.System.entity.User;
import Digital.Library.Management.System.repository.BookRepository;
import Digital.Library.Management.System.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final BookRepository bookRepository;

    public IssueService(IssueRepository issueRepository,
                        BookRepository bookRepository) {
        this.issueRepository = issueRepository;
        this.bookRepository = bookRepository;
    }

    public Issue issueBook(User user, Book book) {

        if (book.getAvailableQuantity() <= 0) {
            throw new IllegalStateException("Book is currently unavailable.");
        }

        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        Issue issue = new Issue();

        issue.setUser(user);
        issue.setBook(book);
        issue.setIssueDate(LocalDate.now());
        issue.setDueDate(LocalDate.now().plusDays(14));
        issue.setStatus("ISSUED");

        return issueRepository.save(issue);
    }

    public Issue returnBook(Long issueId) {

        Optional<Issue> optionalIssue = issueRepository.findById(issueId);

        if (optionalIssue.isEmpty()) {
            throw new IllegalArgumentException("Issue record not found.");
        }

        Issue issue = optionalIssue.get();

        if ("RETURNED".equals(issue.getStatus())) {
            throw new IllegalStateException("Book has already been returned.");
        }

        issue.setReturnDate(LocalDate.now());
        issue.setStatus("RETURNED");

        Book book = issue.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);

        return issueRepository.save(issue);
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    public List<Issue> getIssuesByUser(User user) {
        return issueRepository.findByUser(user);
    }

    public List<Issue> getIssuedBooks() {
        return issueRepository.findByStatus("ISSUED");
    }

    public Optional<Issue> getIssueById(Long id) {
        return issueRepository.findById(id);
    }
}