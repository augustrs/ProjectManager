package com.example.projectmanagerkea.controller;


import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.service.ProjectService;
import com.example.projectmanagerkea.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String homeScreen() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "index";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        try {
            User user = userService.verifyLogIn(username, password);
            if (user != null && user.getRoleId() == 1) {
                session.setAttribute("loggedInUser", user);

                return "redirect:/admin";
            } else if (user != null && user.getRoleId() == 3) {
                session.setAttribute("loggedInUser", user);

                return "redirect:/dashboard";
            } else if (user != null && user.getRoleId() == 2) {
                session.setAttribute("loggedInUser", user);

                return "redirect:/managerDashboard";
            } else {
                model.addAttribute("error", "invalid credentials");
                return "index";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/admin")
    public String admin(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 1) {
            return "admin";
        } else if ((loggedInUser != null && loggedInUser.getRoleId() == 3)) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/managerDashboard")
    public String managerDashboard(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2) {
            return "managerDashboard";
        } else {
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            return "dashboard";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/createUserForm")
    public String showCreateUserForm(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 1) {
            model.addAttribute("user", new User());
            return "createUser";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/createUser")
    public String createUser(HttpSession session, @ModelAttribute("user") User newUser) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 1) {
            userService.createUser(newUser);
            return "redirect:/allUsers";
        }
        return "redirect:/dashboard";
    }
    @GetMapping("/managedProjects")
    public String managedProjects(HttpSession session, Model model) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2) {
            int managerUserId = loggedInUser.getUserId();
            List<Project> projects = userService.findManagedProjects(managerUserId);
            model.addAttribute("projects", projects);
            return "managedProjects";
        }
        return "redirect:/dashboard";
    }
    @GetMapping("/allUsers")
    public String allUsers(HttpSession session, Model model) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 1) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            return "showUsers";
        }
        return "redirect:/dashboard";
    }
}
