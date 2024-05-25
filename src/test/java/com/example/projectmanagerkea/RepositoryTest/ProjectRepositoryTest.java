package com.example.projectmanagerkea.RepositoryTest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.example.projectmanagerkea.repository.ProjectRepository;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;


    @Test
    void findAllProjects() throws SQLException {
        assertEquals(3, projectRepository.getAllProjects().size());
    }

}
