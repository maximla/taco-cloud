package tacos;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import tacos.data.IngredientRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TacoCloudApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class DesignTacoApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void testRecentTacos() throws Exception {
        mockMvc.perform(get("/apiDesign/recent"))
                .andExpect(content().contentType(MediaType.valueOf("application/json")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    public void testGetTacoById() throws Exception {
        mockMvc.perform(get("/apiDesign/0")
                .contentType(MediaType.valueOf("application/json")))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(value = "${security.user.default.userName}", password = "${security.user.default.password}")
    public void testPostTaco() throws Exception {
        Taco taco = new Taco();
        taco.setName("tacoName");
        taco.setIngredients(StreamSupport.stream(ingredientRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(taco);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/apiDesign")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.ingredients", hasSize(taco.getIngredients().size())))
                .andExpect(jsonPath("$.name").value(taco.getName()))
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(result.getResponse().getContentAsString(), Map.class);

        mockMvc.perform(get(String.format( "/apiDesign/%d", map.get("id")))
                .contentType(MediaType.valueOf("application/json")))
                .andExpect(content().contentType(MediaType.valueOf("application/json")))
                .andExpect(status().isOk());
    }
}
