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


@Nested
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



    @Test
    void testLoginSubmit_AdminRole() throws Exception {
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword("admin");
        adminUser.setRoleId(1);

        when(userService.verifyLogIn("admin", "admin")).thenReturn(adminUser);


        mockMvc.perform(post("/login")
                        .param("username", "admin")
                        .param("password", "admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    void testLoginSubmit_NonAdminRole() throws Exception {
        User nonAdminUser = new User();
        nonAdminUser.setUsername("user");
        nonAdminUser.setPassword("user");
        nonAdminUser.setRoleId(3);

        when(userService.verifyLogIn("user", "user")).thenReturn(nonAdminUser);

        mockMvc.perform(post("/login")
                        .param("username", "user")
                        .param("password", "user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    void testLoginSubmit_InvalidCredentials() throws Exception {
        when(userService.verifyLogIn(anyString(), anyString())).thenReturn(null);

        mockMvc.perform(post("/login")
                        .param("username", "invalid")
                        .param("password", "invalid"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"));
    }

    @Test
    void testAdminLogin() throws Exception {
        User adminUser = new User();
        adminUser.setRoleId(1); // Assuming roleId 1 represents admin
        Mockito.when(userService.verifyLogIn(Mockito.anyString(), Mockito.anyString())).thenReturn(adminUser);

        mockMvc.perform(post("/login")
                        .param("username", "admin")
                        .param("password", "adminPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));
    }

    @Test
    void testInvalidCredentials() throws Exception {
        Mockito.when(userService.verifyLogIn(Mockito.anyString(), Mockito.anyString())).thenReturn(null);

        mockMvc.perform(post("/login")
                        .param("username", "invalid")
                        .param("password", "invalidPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("error"));
    }



    @Test
    void testAdminAccessWhenLoggedIn() throws Exception {
        User adminUser = new User();
        adminUser.setRoleId(1);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", adminUser); // Set the loggedInUser attribute

        mockMvc.perform(get("/admin").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    void testRedirectToDashboardForNonAdmin() throws Exception {
        User nonAdminUser = new User();
        nonAdminUser.setRoleId(3);


        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", nonAdminUser);

        mockMvc.perform(get("/admin").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }


    @Test
    void testRedirectToLoginWhenNotLoggedIn() throws Exception {
        // Create a MockHttpSession directly
        MockHttpSession session = new MockHttpSession();

        // Configure the session to return null when getAttribute("loggedInUser") is called
        session.setAttribute("loggedInUser", null);

        mockMvc.perform(get("/admin").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testManagerDashboardAccess() throws Exception {
        User managerUser = new User();
        managerUser.setRoleId(2);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", managerUser);
        mockMvc.perform(get("/managerDashboard").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("managerDashboard"));
    }



}







