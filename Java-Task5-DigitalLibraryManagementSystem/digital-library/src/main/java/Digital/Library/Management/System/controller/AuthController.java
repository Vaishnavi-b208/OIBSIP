package Digital.Library.Management.System.controller;

import Digital.Library.Management.System.entity.User;
import Digital.Library.Management.System.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ==============================
    // LOGIN PAGE
    // ==============================

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // ==============================
    // REGISTER PAGE
    // ==============================

    @GetMapping("/register")
    public String registerPage(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }

    // ==============================
    // REGISTER USER
    // ==============================

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               Model model) {

        if (userService.emailExists(user.getEmail())) {

            model.addAttribute(
                    "error",
                    "Email is already registered."
            );

            return "register";
        }

        // Public registration always creates a normal USER account.
        user.setRole("USER");

        userService.registerUser(user);

        model.addAttribute(
                "success",
                "Registration successful! Please login."
        );

        return "login";
    }

    // ==============================
    // LOGIN USER
    // ==============================

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model,
                            HttpSession session) {

        return userService.findByEmail(email)

                .filter(user ->
                        user.getPassword().equals(password))

                .map(user -> {

                    // Store logged-in user information in session
                    session.setAttribute("userEmail", user.getEmail());
                    session.setAttribute("userName", user.getName());
                    session.setAttribute("userRole", user.getRole());

                    if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                        return "redirect:/admin/dashboard";
                    }

                    return "redirect:/user/dashboard";
                })

                .orElseGet(() -> {

                    model.addAttribute(
                            "error",
                            "Invalid email or password."
                    );

                    return "login";
                });
    }

    // ==============================
    // LOGOUT
    // ==============================

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}