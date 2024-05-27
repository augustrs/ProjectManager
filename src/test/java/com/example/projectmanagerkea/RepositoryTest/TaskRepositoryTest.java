
package com.example.projectmanagerkea.RepositoryTest;


import com.example.projectmanagerkea.model.Task;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.repository.TaskRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;


    @Order(1)
    @Test
    void findTask() throws SQLException {
        Task task = taskRepository.findTask(1);
        assertEquals("Task 1", task.getTaskName());
        assertEquals("Description of Task 1", task.getTaskDescription());
    }

    @Order(2)
    @Test
    void getAssignesForTask() throws SQLException {
        List<User> assignees = taskRepository.getAssigneesForTask(1);
        assertEquals(3, assignees.size());
        assertEquals("Employee", assignees.get(0).getRealName());
    }

    @Order(3)
    @Test
    void createTask() throws SQLException {
        Task newTask = new Task();
        newTask.setTaskName("New Task");
        newTask.setTaskDescription("New Task Description");
        newTask.setTaskTime(10);
        newTask.setTaskPrice(100);
        taskRepository.createTask(newTask, 1);

        Task createdTask = taskRepository.findTask(6);
        assertEquals("New Task", createdTask.getTaskName());
    }

    @Order(4)
    @Test
    void getStatusForTask() throws SQLException {
        String status = taskRepository.getStatusForTask(1);
        assertEquals("Pending", status);
    }

    @Order(5)
    @Test
    void assignUserToTask() throws SQLException {
        taskRepository.assignUserToTask(3, 3);

        List<User> assignees = taskRepository.getAssigneesForTask(3);
        assertTrue(assignees.stream().anyMatch(user -> user.getUserId() == 3));

    }

    @Order(6)
    @Test
    void unassignUsersFromTask() throws SQLException {
        taskRepository.unassignUserFromTask(3, 3);

        List<User> assignees = taskRepository.getAssigneesForTask(3);
        assertFalse(assignees.stream().anyMatch(user -> user.getUserId() == 3));
    }

    @Order(7)
    @Test
    void assignedTasks() throws SQLException {
        List<Task> tasks = taskRepository.assignedTasks(3);
        assertEquals(1, tasks.size());
    }

    @Order(8)
    @Test
    void updateTask() throws SQLException {
        Task task = taskRepository.findTask(1);
        task.setTaskName("Updated Task");
        taskRepository.updateTask(task, 2);

        Task updatedTask = taskRepository.findTask(1);
        assertEquals("Updated Task", updatedTask.getTaskName());
    }

    @Order(9)
    @Test
    void deleteTask() throws SQLException {
        taskRepository.deleteTask(1);
        Task deletedTask = taskRepository.findTask(1);
        assertNull(deletedTask.getTaskName());
    }

}



