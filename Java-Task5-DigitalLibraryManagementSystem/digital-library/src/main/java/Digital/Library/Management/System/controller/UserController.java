package Digital.Library.Management.System.controller;

import Digital.Library.Management.System.entity.User;
import Digital.Library.Management.System.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ==============================
    // USER DASHBOARD
    // ==============================

    @GetMapping("/user/dashboard")
    public String userDashboard(HttpSession session,
                                Model model) {

        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found."));

        model.addAttribute("user", user);

        return "user-dashboard";
    }

    // ==============================
    // USER PROFILE
    // ==============================

    @GetMapping("/user/profile")
    public String profilePage(HttpSession session,
                              Model model) {

        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found."));

        model.addAttribute("user", user);

        return "profile";
    }

    // ==============================
    // UPDATE PROFILE
    // ==============================

    @PostMapping("/user/profile/update")
    public String updateProfile(@ModelAttribute User user,
                                HttpSession session) {

        String oldEmail =
                (String) session.getAttribute("userEmail");

        if (oldEmail == null) {
            return "redirect:/login";
        }

        User existingUser = userService.findByEmail(oldEmail)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found."));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());

        if (user.getPassword() != null &&
            !user.getPassword().isBlank()) {

            existingUser.setPassword(user.getPassword());
        }

        userService.saveUser(existingUser);

        // Update session with new email/name
        session.setAttribute(
                "userEmail",
                existingUser.getEmail()
        );

        session.setAttribute(
                "userName",
                existingUser.getName()
        );

        return "redirect:/user/profile";
    }
}