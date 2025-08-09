import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorizationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginReturnsOkForValidCredentials() throws Exception {
        mockMvc.perform(post("/api/login/alice").param("Authorization", "password"))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));
    }

    @Test
    void loginReturnsForbiddenForInvalidCredentials() throws Exception {
        mockMvc.perform(post("/api/login/alice").param("Authorization", "wrong"))
                .andExpect(status().isForbidden());
    }
}

