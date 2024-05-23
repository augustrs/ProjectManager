package com.example.projectmanagerkea.ControllerTest;
import com.example.projectmanagerkea.controller.ProjectController;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.service.ProjectService;
import com.example.projectmanagerkea.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
@WebMvcTest(ProjectController.class)
class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;



    @Mock
    private Model model;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowCreateProjectForm() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setRoleId(2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);

        mockMvc.perform(get("/createProjectForm").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("createProject"));
    }

    @Test
    void testCreateProject() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setRoleId(2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);

        mockMvc.perform(post("/createProject")
                        .session(session)
                        .param("projectName", "New Project")
                        .param("description", "Project Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/managerDashboard"));
    }

    @Test
    void testAllProjects() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setRoleId(1);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);

        mockMvc.perform(get("/allProjects").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("allProjects"));
    }

    @Test
    void testSubprojects() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setRoleId(2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);

        mockMvc.perform(get("/1/subprojects").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("subprojects"));
    }

    @Test
    void testTasks() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setRoleId(2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);

        mockMvc.perform(get("/1/tasks").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks"));
    }
/*
    @Test
    void testEditSubProjectForm() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setRoleId(2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);

        mockMvc.perform(get("/1/editSubProject").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("editSubProject"));
    }
*/
    @Test
    void testEditSubProject() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setRoleId(2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);

        mockMvc.perform(post("/editSubProject")
                        .session(session)
                        .param("projectId", "1")
                        .param("projectName", "Edited Project")
                        .param("description", "Edited Project Description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/allProjects"));
    }

    @Test
    void testDeleteSubProject() throws Exception {
        User user = new User();
        user.setUserId(1);
        user.setRoleId(2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", user);

        mockMvc.perform(post("/1/deleteSubProject").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/allProjects"));
    }
}







