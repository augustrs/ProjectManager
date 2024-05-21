package com.example.projectmanagerkea.service;

import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User verifyLogIn(String username, String password) {
        try {
            return userRepository.verifyUser(username, password);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public void createUser(User newUser) {
        try {
            userRepository.createUser(newUser);
        } catch (RuntimeException e) {
            throw e;
        }
    }


    public List<Project> findManagedProjects(int managerUserId) {
        try {
            return userRepository.findManagedProjects(managerUserId);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public List<User> getAllUsers() {
        try {
            return userRepository.getAllUsers();
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public List<User> getAllEmployees() {
        try {
            return userRepository.getAllEmployees();
        } catch (RuntimeException e) {
            throw e;
        }
    }
}
