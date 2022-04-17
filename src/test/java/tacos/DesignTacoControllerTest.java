package tacos;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import tacos.data.IngredientRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = TacoCloudApplication.class)
@AutoConfigureMockMvc
public class DesignTacoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    IngredientRepository ingredientRepository;

    @Test
    public void testDesignPage() throws Exception {
        mockMvc.perform(get("/design"))
                .andExpect(content().contentType(MediaType.valueOf("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(view().name("design"));
    }

    @Test
    public void testProcessDesignInvalidArgs() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/design")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("design"));
    }

    @Test
    public void testProcessDesignInvalidName() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/design")
                .param("name", "abc")
                .param("ingredients", new String[]{ingredientRepository.findAll().get(0).getId()})
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("design"));
    }

    @Test
    public void testProcessDesignInvalidIngredients() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/design")
                .param("name", "abcde")
                .param("ingredients", new String[]{""})
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("design"));
    }

    @Test
    public void testProcessDesignValidArgs() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/design")
                .param("name", "abcde")
                .param("ingredients", new String[]{ingredientRepository.findAll().get(0).getId()})
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/current"));
    }
}
