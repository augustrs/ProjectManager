package com.example.projectmanagerkea.ControllerTest;

import com.example.projectmanagerkea.controller.ProjectController;
import com.example.projectmanagerkea.controller.UserController;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.service.ProjectService;
import com.example.projectmanagerkea.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
public class UserControllerTest {



    @Autowired
    private MockMvc mockMvc;



    @MockBean
    private UserService userService;

    @Test
    void testLoginSubmit_AdminRole() throws Exception {
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword("admin");
        adminUser.setRoleId(1); // Set the roleId to 1 to indicate an admin user

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
        nonAdminUser.setRoleId(3); // Set the roleId to 3 to indicate a non-admin user

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
        adminUser.setRoleId(1); // Set the roleId to 1 to indicate an admin user
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
        adminUser.setRoleId(1); // Set the roleId to 1 to indicate an admin user
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", adminUser);

        mockMvc.perform(get("/admin").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }

    @Test
    void testRedirectToDashboardForNonAdmin() throws Exception {
        User nonAdminUser = new User();
        nonAdminUser.setRoleId(3); // Set the roleId to 3 to indicate a non-admin user


        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", nonAdminUser);

        mockMvc.perform(get("/admin").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }


    @Test
    void testRedirectToLoginWhenNotLoggedIn() throws Exception {

        MockHttpSession session = new MockHttpSession();

        session.setAttribute("loggedInUser", null);

        mockMvc.perform(get("/admin").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testManagerDashboardAccess() throws Exception {
        User managerUser = new User();
        managerUser.setRoleId(2); // Set the roleId to 2 to indicate a manager user
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loggedInUser", managerUser);
        mockMvc.perform(get("/managerDashboard").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("managerDashboard"));
    }


}
