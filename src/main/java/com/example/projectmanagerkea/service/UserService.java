package com.example.projectmanagerkea.service;

import com.example.projectmanagerkea.model.Project;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.repository.ProjectRepository;
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

    public int findManagerId(int userId) throws SQLException {
        return userRepository.findManagerId(userId);
    }
    public List<Project> findProjectsByManagerId(int managerId) throws SQLException {
        return userRepository.findProjects(managerId);
    }
}
