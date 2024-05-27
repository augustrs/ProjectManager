package com.example.projectmanagerkea.RepositoryTest;


import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Order(1)
    @Test
    void findUser() throws SQLException {
        assertEquals("Admin", userRepository.findUserFromId(1).getRealName());
    }

    @Order(2)
    @Test
    void verifyUser() throws SQLException {
        User user = userRepository.verifyUser("admin", "123");
        assertNotNull(user);
        assertEquals("admin",user.getUsername());

        User invalidUser = userRepository.verifyUser("admin","wrong pw");
        assertNull(invalidUser);
    }

    @Order(3)
    @Test
    void createUser() throws SQLException {
        User newUser = new User();
        newUser.setRealName("New User");
        newUser.setUsername("newuser");
        newUser.setPassword("123");
        newUser.setRoleId(2);

        userRepository.createUser(newUser);

        User createdUser = userRepository.findUser("newuser");
        assertNotNull(createdUser);
        assertEquals("New User", createdUser.getRealName());
    }

    @Order(4)
    @Test
    void findManagedProjects() throws SQLException {
        List<Project> projects = userRepository.findManagedProjects(2);
        assertNotNull(projects);
        assertFalse(projects.isEmpty());
    }

    @Order(5)
    @Test
    void getAllUsers() throws SQLException {
        List<User> users = userRepository.getAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Order(6)
    @Test
    void getAllEmployees() throws SQLException {
        List<User> employees = userRepository.getAllEmployees();
       assertNotNull(employees);
       assertFalse(employees.isEmpty());
       assertTrue(employees.stream().allMatch(user -> user.getRoleId() == 3));
    }
}
