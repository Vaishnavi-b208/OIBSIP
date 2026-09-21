package Digital.Library.Management.System.service;

import Digital.Library.Management.System.entity.Fine;
import Digital.Library.Management.System.entity.Issue;
import Digital.Library.Management.System.entity.User;
import Digital.Library.Management.System.repository.FineRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FineService {

    private static final double FINE_PER_DAY = 5.0;

    private final FineRepository fineRepository;

    public FineService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    /**
     * Calculates the fine for an overdue issue.
     *
     * Fine = Number of overdue days × ₹5
     *
     * If a fine already exists for this issue, it is updated
     * instead of creating a duplicate fine.
     */
    public Fine calculateFine(Issue issue) {

        LocalDate endDate;

        if (issue.getReturnDate() != null) {
            endDate = issue.getReturnDate();
        } else {
            endDate = LocalDate.now();
        }

        // No fine if the book was returned on or before the due date.
        if (!endDate.isAfter(issue.getDueDate())) {
            return null;
        }

        long overdueDays = ChronoUnit.DAYS.between(
                issue.getDueDate(),
                endDate
        );

        double amount = overdueDays * FINE_PER_DAY;

        // Check whether a fine already exists for this issue.
        Fine fine = fineRepository.findByIssue(issue)
                .orElseGet(Fine::new);

        fine.setUser(issue.getUser());
        fine.setIssue(issue);
        fine.setAmount(amount);

        // Only set unpaid when creating a new fine.
        if (fine.getId() == null) {
            fine.setPaid(false);
        }

        return fineRepository.save(fine);
    }

    public List<Fine> getAllFines() {
        return fineRepository.findAll();
    }

    public List<Fine> getFinesByUser(User user) {
        return fineRepository.findByUser(user);
    }

    public List<Fine> getUnpaidFines() {
        return fineRepository.findByPaid(false);
    }

    public List<Fine> getPaidFines() {
        return fineRepository.findByPaid(true);
    }

    public Fine markAsPaid(Long fineId) {

        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Fine not found."));

        fine.setPaid(true);

        return fineRepository.save(fine);
    }
}