package tacos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import tacos.data.UserRepository;
import tacos.security.DefaultUserHolder;
import tacos.security.PasswordEncoderHolder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = TacoCloudApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DefaultUserHolder defaultUserHolder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoderHolder passwordEncoderHolder;

    @BeforeEach
    public void init() {
        User defaultUser = userRepository.findByUsername(defaultUserHolder.DEFAULT_USER_USER_NAME);
        if(defaultUser == null){
            User finalUser = new User(defaultUserHolder.DEFAULT_USER_USER_NAME,
                    passwordEncoderHolder.getInstance().encode(defaultUserHolder.DEFAULT_USER_PASSWORD),
                    defaultUserHolder.DEFAULT_USER_NAME,
                    defaultUserHolder.DEFAULT_USER_STREET,
                    defaultUserHolder.DEFAULT_USER_CITY,
                    defaultUserHolder.DEFAULT_USER_STATE,
                    defaultUserHolder.DEFAULT_USER_ZIP,
                    defaultUserHolder.DEFAULT_USER_PN);
            userRepository.save(finalUser);
            defaultUserHolder.setUser(finalUser);
        }
    }

//    @Test
//    public void testOrderForm() throws Exception {
//        mockMvc.perform(get("/orders/current"))
//                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
//                .andExpect(status().isOk())
//                .andExpect(view().name("orderForm"));
//    }

    @Test
    @WithMockUser(value = "user", password = "123")
    public void testProcessOrderInvalid() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("orderForm"));
    }

    @Test
    public void testProcessOrderValid() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/orders")
                .param("name", "name")
                .param("street", "street")
                .param("city", "city")
                .param("state", "state")
                .param("zip", "zip")
                .param("ccNumber", "1111222233334444")
                .param("ccExpiration", "12/12")
                .param("ccCVV", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .with(user(defaultUserHolder.getUser()))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
