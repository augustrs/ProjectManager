package com.example.projectmanagerkea.ControllerTest;

import com.example.projectmanagerkea.controller.TaskController;
import com.example.projectmanagerkea.model.Task;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.service.TaskService;
import com.example.projectmanagerkea.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @MockBean
    private HttpSession session;

    @Test
    public void testShowTask() throws Exception {
        // Set up mock data
        User loggedInUser = new User();
        loggedInUser.setUserId(1);
        loggedInUser.setRoleId(2);

        Task task = new Task();
        task.setTaskId(1);

        List<User> assignees = new ArrayList<>();
        assignees.add(new User());

        List<User> allEmployees = new ArrayList<>();
        allEmployees.add(new User());

        // Configure mocks
        Mockito.when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);
        Mockito.when(taskService.getAssigneesForTask(anyInt())).thenReturn(assignees);
        Mockito.when(taskService.findTask(anyInt())).thenReturn(task);
        Mockito.when(taskService.getStatusForTask(anyInt())).thenReturn("In Progress");
        Mockito.when(userService.getAllEmployees()).thenReturn(allEmployees);

        // Perform request and assert results
        mockMvc.perform(MockMvcRequestBuilders.get("/1/showTask")
                        .sessionAttr("loggedInUser", loggedInUser))
                .andExpect(status().isOk())
                .andExpect(view().name("showTask"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("assignees"))
                .andExpect(model().attributeExists("status"))
                .andExpect(model().attributeExists("allEmployees"));
    }

    @Test
    public void testAssignUser() throws Exception {
        User loggedInUser = new User();
        loggedInUser.setUserId(1);
        loggedInUser.setRoleId(2);

        Mockito.when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/1/assignUser")
                        .param("userId", "2")
                        .sessionAttr("loggedInUser", loggedInUser))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/1/showTask"));
    }

    @Test
    public void testUnassignUser() throws Exception {
        User loggedInUser = new User();
        loggedInUser.setUserId(1);
        loggedInUser.setRoleId(2);

        Mockito.when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/1/unassignUser")
                        .param("userId", "2")
                        .sessionAttr("loggedInUser", loggedInUser))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/1/showTask"));
    }

    @Test
    public void testAssignedTasks() throws Exception {
        User loggedInUser = new User();
        loggedInUser.setUserId(1);

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());

        Mockito.when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);
        Mockito.when(taskService.assignedTasksForUser(anyInt())).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/assignedTasks")
                        .sessionAttr("loggedInUser", loggedInUser))
                .andExpect(status().isOk())
                .andExpect(view().name("assignedTasks"))
                .andExpect(model().attributeExists("tasks"));
    }

    @Test
    public void testShowCreateTaskForm() throws Exception {
        User loggedInUser = new User();
        loggedInUser.setUserId(1);

        Mockito.when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/1/createTask")
                        .sessionAttr("loggedInUser", loggedInUser))
                .andExpect(status().isOk())
                .andExpect(view().name("createTask"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attributeExists("subprojectId"));
    }

    @Test
    public void testCreateTask() throws Exception {
        User loggedInUser = new User();
        loggedInUser.setUserId(1);

        Mockito.when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/1/createTask")
                        .sessionAttr("loggedInUser", loggedInUser)
                        .param("task", new Task().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/1/tasks"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        User loggedInUser = new User();
        loggedInUser.setUserId(1);
        loggedInUser.setRoleId(2);

        Mockito.when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/1/1/deleteTask")
                        .sessionAttr("loggedInUser", loggedInUser))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/1/tasks"));
    }

    @Test
    public void testShowUpdateTaskForm() throws Exception {
        User loggedInUser = new User();
        loggedInUser.setUserId(1);

        Task taskToUpdate = new Task();
        taskToUpdate.setTaskId(1);

        Mockito.when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);
        Mockito.when(taskService.findTask(anyInt())).thenReturn(taskToUpdate);

        mockMvc.perform(MockMvcRequestBuilders.get("/1/updateTask")
                        .sessionAttr("loggedInUser", loggedInUser))
                .andExpect(status().isOk())
                .andExpect(view().name("updateTask"))
                .andExpect(model().attributeExists("task"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        User loggedInUser = new User();
        loggedInUser.setUserId(1);

        Task updatedTask = new Task();
        updatedTask.setTaskId(1);

        Mockito.when(session.getAttribute("loggedInUser")).thenReturn(loggedInUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/1/updateTask")
                        .sessionAttr("loggedInUser", loggedInUser)
                        .param("taskStatus", "2")
                        .flashAttr("task", updatedTask))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/1/showTask"));
    }
}
