package Digital.Library.Management.System.controller;

import Digital.Library.Management.System.entity.Query;
import Digital.Library.Management.System.entity.User;
import Digital.Library.Management.System.repository.QueryRepository;
import Digital.Library.Management.System.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class QueryController {

    private final QueryRepository queryRepository;
    private final UserService userService;

    public QueryController(
            QueryRepository queryRepository,
            UserService userService) {

        this.queryRepository = queryRepository;
        this.userService = userService;
    }

    // Open Contact / Query page
    @GetMapping("/contact")
    public String contactPage(
            HttpSession session,
            Model model) {

        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found."));

        model.addAttribute("user", user);
        model.addAttribute("query", new Query());

        return "contact";
    }

    // Submit query
    @PostMapping("/contact")
    public String submitQuery(
            @ModelAttribute("query") Query query,
            HttpSession session,
            Model model) {

        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found."));

        query.setUser(user);
        query.setCreatedAt(LocalDateTime.now());
        query.setStatus("PENDING");

        queryRepository.save(query);

        return "redirect:/contact?success=true";
    }
}