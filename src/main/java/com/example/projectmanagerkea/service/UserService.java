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

    public User verifyLogIn(String username, String password) throws SQLException {
        return userRepository.verifyUser(username,password);
    }

    public void createUser(User newUser) throws SQLException {
        userRepository.createUser(newUser);
    }


    public List<Project> findManagedProjects(int managerUserId) throws SQLException {
        return userRepository.findManagedProjects(managerUserId);
    }
}
