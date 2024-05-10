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
        if (loggedInUser != null) {
            List<User> assignees = projectService.getAssigneesForTask(taskId);
            Task task = projectService.findTask(taskId);
            String status = projectService.getStatusForTask(taskId);
            model.addAttribute("task", task);
            model.addAttribute("assignees", assignees);
            model.addAttribute("status", status);
            if (loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1) {
                List<User> allEmployees = userService.getAllEmployees();
                List<User> unassignedEmployees = new ArrayList<>();
                for (User employee : allEmployees) {
                    boolean isAssigned = false;
                    for (User assignee : assignees) {
                        if (assignee.getUserId() == employee.getUserId()) {
                            isAssigned = true;
                            break;
                        }
                    }
                    if (!isAssigned) {
                        unassignedEmployees.add(employee);
                    }
                }

                model.addAttribute("allEmployees", unassignedEmployees);
                return "showTask";
            } else if (loggedInUser.getRoleId() == 3){
                return "showTaskEmp";
            }
        }
        return "redirect:/dashboard";
    }



    @PostMapping("/{taskId}/assignUser")
    public String assignUser(@PathVariable int taskId, @RequestParam int userId, HttpSession session) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1) {
            projectService.assignUserToTask(taskId, userId);
            return "redirect:/{taskId}/showTask";
        }
        return "redirect:/dashboard";
    }


    @PostMapping("/{taskId}/unassignUser")
    public String unassignUser(@PathVariable int taskId, @RequestParam int userId, HttpSession session) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1) {
            projectService.unassignUserFromTask(taskId, userId);
            return "redirect:/{taskId}/showTask";
        }
        return "redirect:/{taskId}/showTask";
    }
    @GetMapping("/assignedTasks")
    public String assignedTasks(HttpSession session, Model model) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            List<Task> tasks = projectService.assignedTasksForUser(loggedInUser.getUserId());
            model.addAttribute("tasks", tasks);
            return "assignedTasks";
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/{taskId}/updateTask")
    public String showUpdateTaskForm(@PathVariable int taskId, HttpSession session, Model model) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            Task taskToUpdate = projectService.findTask(taskId);

            if (taskToUpdate != null) {
                model.addAttribute("task", taskToUpdate);

                return "updateTask";
            } else {
                return "error";
            }
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/{taskId}/updateTask")
    public String updateTask(@ModelAttribute("task") Task updatedTask, HttpSession session) throws SQLException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            projectService.updateTask(updatedTask);
            return "redirect:/{taskId}/showTask";
        }

        return "redirect:/dashboard";
    }


}
