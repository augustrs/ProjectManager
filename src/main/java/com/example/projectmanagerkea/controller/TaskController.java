package com.example.projectmanagerkea.controller;

import com.example.projectmanagerkea.model.Task;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.service.ProjectService;
import com.example.projectmanagerkea.service.TaskService;
import com.example.projectmanagerkea.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class TaskController {

    private TaskService taskService;
    private UserService userService;


    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/{taskId}/showTask")
    public String showTask(@PathVariable int taskId, HttpSession session, Model model) {
        try {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {

            List<User> assignees = taskService.getAssigneesForTask(taskId);
            Task task = taskService.findTask(taskId);
            String status = taskService.getStatusForTask(taskId);
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
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
        return "redirect:/dashboard";
    }



    @PostMapping("/{taskId}/assignUser")
    public String assignUser(@PathVariable int taskId, @RequestParam int userId, HttpSession session, Model model) {
        try {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1) {
            taskService.assignUserToTask(taskId, userId);
            return "redirect:/{taskId}/showTask";
        }
        return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }


    @PostMapping("/{taskId}/unassignUser")
    public String unassignUser(@PathVariable int taskId, @RequestParam int userId, HttpSession session, Model model) {
        try {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1) {
            taskService.unassignUserFromTask(taskId, userId);
            return "redirect:/{taskId}/showTask";
        }
        return "redirect:/{taskId}/showTask";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }


    @GetMapping("/assignedTasks")
    public String assignedTasks(HttpSession session, Model model) {
        try {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            List<Task> tasks = taskService.assignedTasksForUser(loggedInUser.getUserId());
            model.addAttribute("tasks", tasks);
            return "assignedTasks";
        }
        return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{taskId}/updateTask")
    public String showUpdateTaskForm(@PathVariable int taskId, HttpSession session, Model model)  {
        try {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            Task taskToUpdate = taskService.findTask(taskId);

            if (taskToUpdate != null) {
                model.addAttribute("task", taskToUpdate);

                return "updateTask";
            } else {
                return "error";
            }
        }
        return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/{taskId}/updateTask")
    public String updateTask(@ModelAttribute("task") Task updatedTask, @RequestParam("taskStatus") int statusId, HttpSession session, Model model) {
        try {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            taskService.updateTask(updatedTask, statusId);
            return "redirect:/{taskId}/showTask";
        }

        return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
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
    public String createTask(HttpSession session, @ModelAttribute("task") Task newTask, @PathVariable int subprojectId, Model model) {
        try {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            taskService.createTask(newTask, subprojectId);
            return "redirect:/{subprojectId}/tasks";
        }
        return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/{subprojectId}/{taskId}/deleteTask")
    public String deleteTask(@PathVariable int subprojectId, @PathVariable int taskId, HttpSession session, Model model)  {
        try {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null && (loggedInUser.getRoleId() == 2 || loggedInUser.getRoleId() == 1)) {
            taskService.deleteTask(taskId);
            return "redirect:/{subprojectId}/tasks";
        }
        return "redirect:/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

}
