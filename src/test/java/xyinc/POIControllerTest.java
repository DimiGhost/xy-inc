package xyinc;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import xyinc.model.POI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class POIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    
    @Test
    public void testeListarPOI() throws Exception {
        this.mockMvc.perform(get("/poi/listar")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    public void testeProcurarPOI() throws Exception {
        this.mockMvc.perform(get("/poi/procurar")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    public void testeCadastrarPOISemConteudo() throws Exception {
        this.mockMvc.perform(post("/poi/cadastrar")).andDo(print()).andExpect(status().isBadRequest());
    }
    
    @Test
    public void testeCadastrarPOISucesso() throws Exception {
        POI poi = new POI("Estabelecimento", 10, 10);
        
        this.mockMvc.perform(post("/poi/cadastrar").contentType("application/json").content(objectMapper.writeValueAsString(poi))).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").value("POI cadastrado com sucesso."));
    }
    
    @Test
    public void testeCadastrarPOICoordenadaNegativa() throws Exception {
        POI poi = new POI("Estabelecimento", -10, 10);
        
        this.mockMvc.perform(post("/poi/cadastrar").contentType("application/json").content(objectMapper.writeValueAsString(poi))).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("As coordenadas X e Y não podem ser negativas."));
    }
    
    @Test
    public void testeCadastrarPOISemNome() throws Exception {
        POI poi = new POI("", -10, 10);
        
        this.mockMvc.perform(post("/poi/cadastrar").contentType("application/json").content(objectMapper.writeValueAsString(poi))).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("O nome do POI deve ser preenchido."));
    }
    
    @Test
    public void testeCadastrarPOISemCoordenada() throws Exception {
        POI poi = new POI();
        poi.setNome("Estabelecimento");
        poi.setCoordenadaX(10);
        
        this.mockMvc.perform(post("/poi/cadastrar").contentType("application/json").content(objectMapper.writeValueAsString(poi))).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("As coordenadas X e Y do POI devem ser preenchidas."));
    }

}
