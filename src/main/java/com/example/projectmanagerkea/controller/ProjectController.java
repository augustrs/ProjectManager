package com.example.projectmanagerkea.controller;

import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.model.Task;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.service.ProjectService;

import com.example.projectmanagerkea.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

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
          //  int managerId = userService.findManagerId(loggedInUser.getUserId());
         //   projectService.createProject(newProject, managerId);
            return "redirect:/managerDashboard";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("{subprojectId}/createTask")
    public String showCreateTaskForm(HttpSession session, Model model, @PathVariable int subprojectId) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("task", new Task());
            model.addAttribute("subprojectId", subprojectId);
            return "createTask";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("{subprojectId}/createTask")
    public String createTask(HttpSession session, @ModelAttribute("task") Task newTask, @PathVariable int subprojectId) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            projectService.createTask(newTask, subprojectId);
            return "redirect:/{subprojectId}/tasks";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login"; // Redirect to the login page
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
    @GetMapping("/allProjects")
    public String allProjects(HttpSession session, Model model) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 1) {
            List<Project> projects = projectService.getAllProjects();
            model.addAttribute("projects", projects);
            return "allProjects";
        }
        return "redirect:/dashboard";
    }


    @GetMapping("/{projectId}/subprojects")
public String subprojects(@PathVariable int projectId, HttpSession session, Model model) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2 || loggedInUser != null && loggedInUser.getRoleId() == 1) {
            List<Project> subprojects = projectService.findSubprojectsForProject(projectId);
            model.addAttribute("subprojects", subprojects);
            return "subprojects";
        }
        return "redirect:/dashboard";
    }
    @GetMapping("/{subProjectId}/tasks")
    public String tasks(@PathVariable int subProjectId, HttpSession session, Model model) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2 || loggedInUser != null && loggedInUser.getRoleId() == 1) {
            List<Task> tasks = projectService.findTasksForSubProject(subProjectId);
            model.addAttribute("tasks", tasks);
            return "tasks";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/{taskId}/showTask")
    public String showTask(@PathVariable int taskId, HttpSession session, Model model) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null ) {
            List<User> assignees = projectService.getAssigneesForTask(taskId);
            Task task = projectService.findTask(taskId);
            model.addAttribute("task", task);
            model.addAttribute("assignees", assignees);
            return "showTask";
        }
        return "redirect:/dashboard";
    }


}
