package Digital.Library.Management.System.controller;

import Digital.Library.Management.System.entity.Fine;
import Digital.Library.Management.System.entity.User;
import Digital.Library.Management.System.service.FineService;
import Digital.Library.Management.System.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FineController {

    private final FineService fineService;
    private final UserService userService;

    public FineController(FineService fineService, UserService userService) {
        this.fineService = fineService;
        this.userService = userService;
    }

    // ==============================
    // VIEW UNPAID FINES
    // ==============================

    @GetMapping("/admin/fines/unpaid")
    public String viewUnpaidFines(Model model) {

        List<Fine> fines = fineService.getUnpaidFines();

        model.addAttribute("fines", fines);
        model.addAttribute("filter", "unpaid");

        return "fines";
    }

    // ==============================
    // VIEW PAID FINES
    // ==============================

    @GetMapping("/admin/fines/paid")
    public String viewPaidFines(Model model) {

        List<Fine> fines = fineService.getPaidFines();

        model.addAttribute("fines", fines);
        model.addAttribute("filter", "paid");

        return "fines";
    }

    // ==============================
    // USER FINES
    // ==============================

    @GetMapping("/user/fines")
    public String myFines(
            @RequestParam String email,
            Model model) {

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found."));

        model.addAttribute(
                "fines",
                fineService.getFinesByUser(user)
        );

        model.addAttribute("user", user);

        return "fines";
    }

    // ==============================
    // MARK FINE AS PAID
    // ==============================

    @PostMapping("/admin/fines/pay/{id}")
    public String markAsPaid(@PathVariable Long id) {

        fineService.markAsPaid(id);

        return "redirect:/admin/fines";
    }
}