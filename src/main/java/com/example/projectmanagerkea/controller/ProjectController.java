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
import java.util.ArrayList;
import java.util.List;

@Controller

public class ProjectController {

    private ProjectService projectService;
    private UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }



    @GetMapping("/createProjectForm")
    public String showCreateProjectForm(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2) {
            model.addAttribute("project", new Project());
            return "createProject";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/createProject")
    public String createProject(HttpSession session, @ModelAttribute("project") Project newProject) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2) {
            //  int managerId = userService.findManagerId(loggedInUser.getUserId());
            //   projectService.createProject(newProject, managerId);
            return "redirect:/managerDashboard";
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




}
