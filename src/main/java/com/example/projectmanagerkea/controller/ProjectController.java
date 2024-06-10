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
        if (loggedInUser != null && loggedInUser.getRoleId() == 2 || loggedInUser != null && loggedInUser.getRoleId() == 1) {
            model.addAttribute("project", new Project());
            return "createProject";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/createProject")
    public String createProject(HttpSession session, @ModelAttribute("project") Project newProject, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2 || loggedInUser != null && loggedInUser.getRoleId() == 1) {
            int managerId = loggedInUser.getUserId();
            try {
                projectService.createProject(newProject, managerId);
                if (loggedInUser.getRoleId() == 2) {
                    return "redirect:/managerDashboard";
                }
                return "redirect:/allProjects";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
        }
        return "redirect:/dashboard";
    }


    @GetMapping("/allProjects")
    public String allProjects(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 1 || loggedInUser != null && loggedInUser.getRoleId() == 2) {
            try {
                List<Project> projects = projectService.getAllProjects();
                model.addAttribute("projects", projects);
                return "allProjects";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
        }
        return "redirect:/dashboard";
    }


    @GetMapping("/{projectId}/subprojects")
    public String subprojects(@PathVariable int projectId, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2 || loggedInUser != null && loggedInUser.getRoleId() == 1) {
            try {
                List<Project> subprojects = projectService.findSubprojectsForProject(projectId);
                model.addAttribute("subprojects", subprojects);
                return "subprojects";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/{subProjectId}/tasks")
    public String tasks(@PathVariable int subProjectId, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2 || loggedInUser != null && loggedInUser.getRoleId() == 1) {
            try {
                List<Task> tasks = projectService.findTasksForSubProject(subProjectId);
                model.addAttribute("tasks", tasks);
                return "tasks";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
        }
        return "redirect:/dashboard";
    }


    @GetMapping("{subProjectId}/editSubProject")
    public String editSubProject(@PathVariable int subProjectId, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && (loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1)) {
            try {
                Project subProject = projectService.findSubProject(subProjectId);
                model.addAttribute("subProject", subProject);
                model.addAttribute("subProjectId", subProjectId);
                return "editSubProject";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/editSubProject")
    public String editSubProject(@ModelAttribute("subProject") Project subProject, HttpSession session, Model model)  {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && (loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1)) {
            try { projectService.editSubProject(subProject);
                return "redirect:/allProjects";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/{subProjectId}/deleteSubProject")
    public String deleteSubProject(@PathVariable int subProjectId, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && (loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1)) {
            try {
            projectService.deleteSubProject(subProjectId);
            return "redirect:/allProjects";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/{projectId}/createSubProjectForm")
    public String createSubProject(@PathVariable int projectId, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && (loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1)) {
            model.addAttribute("subProject", new Project());
            model.addAttribute("projectId", projectId);
            return "createSubproject";
        }
        return "redirect:/dashboard";
    }
    @PostMapping("/{projectId}/createSubproject")
    public String createSubProject(@ModelAttribute("subProject") Project newSubProject, @PathVariable int projectId, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && (loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1)) {
            try {
                projectService.createSubProject(newSubProject, projectId);
                return "redirect:/{projectId}/subprojects";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/{projectId}/deleteProject")
    public String deleteProject(@PathVariable int projectId, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && (loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1)) {
            try {
                projectService.deleteProject(projectId);
                return "redirect:/allProjects";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/{projectId}/editProject")
    public String editProject(@PathVariable int projectId, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && (loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1)) {
            try {
                Project project = projectService.findProject(projectId);
                model.addAttribute("project", project);
                model.addAttribute("projectId", projectId);
                return "editProject";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
        }
        return "redirect:/dashboard";
    }
    @PostMapping("/editProject")
    public String editProject(@ModelAttribute("project") Project project, HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && (loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1)) {
            try {
                projectService.editProject(project);
                return "redirect:/allProjects";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "error";
            }
        }
        return "redirect:/dashboard";
    }

}
