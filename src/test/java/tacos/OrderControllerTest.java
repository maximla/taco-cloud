package tacos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = TacoCloudApplication.class)
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void testOrderForm() throws Exception {
//        mockMvc.perform(get("/orders/current"))
//                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
//                .andExpect(status().isOk())
//                .andExpect(view().name("orderForm"));
//    }

    @Test
    public void testProcessOrderInvalid() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
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
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
