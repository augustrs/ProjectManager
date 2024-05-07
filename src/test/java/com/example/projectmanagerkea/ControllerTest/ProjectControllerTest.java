package com.example.projectmanagerkea.ControllerTest;
import com.example.projectmanagerkea.controller.ProjectController;
import com.example.projectmanagerkea.model.User;
import com.example.projectmanagerkea.service.ProjectService;
import com.example.projectmanagerkea.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

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

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "admin")
                        .param("password", "admin"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin"));
    }

    @Test
    void testLoginSubmit_NonAdminRole() throws Exception {
        User nonAdminUser = new User();
        nonAdminUser.setUsername("user");
        nonAdminUser.setPassword("user");
        nonAdminUser.setRoleId(3);

        when(userService.verifyLogIn("user", "user")).thenReturn(nonAdminUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "user")
                        .param("password", "user"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/dashboard"));
    }

    @Test
    void testLoginSubmit_InvalidCredentials() throws Exception {
        when(userService.verifyLogIn(anyString(), anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "invalid")
                        .param("password", "invalid"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("error"));
    }
}
