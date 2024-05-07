package com.example.projectmanagerkea.controller;

import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.model.Task;
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

@Controller

public class ProjectController {

    private ProjectService projectService;
    private UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
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
            if (user!=null && user.getRoleId() == 1) {
                session.setAttribute("loggedInUser", user);

                return "redirect:/admin";
            } else if (user!=null && user.getRoleId() == 3){
                session.setAttribute("loggedInUser", user);

                return "redirect:/dashboard";
            } else if (user!=null && user.getRoleId() == 2){
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
    public String showCreateUserForm(HttpSession session,Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 1) {
            model.addAttribute("user", new User());
            return "createUser";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/createUser")
    public String createUser(HttpSession session,@ModelAttribute("user") User newUser) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 1) {
            userService.createUser(newUser);
            return "redirect:/admin";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/createProjectForm")
    public String showCreateProjectForm(HttpSession session,Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2) {
            model.addAttribute("project", new Project());
            return "createProject";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/createProject")
    public String createProject(HttpSession session,@ModelAttribute("project") Project newProject) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2) {
            int managerId = userService.findManagerId(loggedInUser.getUserId());
            projectService.createProject(newProject, managerId);
            return "redirect:/managerDashboard";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/createTaskForm")
    public String showCreateTaskForm(HttpSession session,Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2) {
            model.addAttribute("task", new Task());
            return "createTask";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/createTask")
    public String createTask(HttpSession session,@ModelAttribute("task") Task newTask) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2) {
            int projectId = 0;
            projectService.createTask(newTask, projectId);
            return "redirect:/dashboard";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login"; // Redirect to the login page
    }

}
