package com.example.projectmanagerkea.controller;



import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.service.ProjectService;

import com.example.projectmanagerkea.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;


@Controller

public class ProjectController {

    private ProjectService projectService;
    private UserService userService;
    public ProjectController(ProjectService projectService, UserService userService ){
        this.projectService = projectService;
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


    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        try {
            User user = userService.verifyLogIn(username,password);
            if (user!=null && user.getRole().equals("ADMIN")) {
                session.setAttribute("loggedInUser", user);

                return "redirect:/admin";
            } else if (user!=null && user.getRole().equals("USER")){
                session.setAttribute("loggedInUser", user);

                return "redirect:/dashboard";
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
        if (loggedInUser != null && loggedInUser.getRole().equals("ADMIN")) {
            return "admin";
        } else if ((loggedInUser != null && loggedInUser.getRole().equals("USER"))) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/login";
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
}
