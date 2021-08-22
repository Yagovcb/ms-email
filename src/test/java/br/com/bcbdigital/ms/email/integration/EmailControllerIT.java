package br.com.bcbdigital.ms.email.integration;

import br.com.bcbdigital.ms.email.MsEmailApplication;
import br.com.bcbdigital.ms.email.mock.EmailMock;
import br.com.bcbdigital.ms.email.services.dto.EmailDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = MsEmailApplication.class)
@DisplayName("EmailController - Teste de Integração")
public class EmailControllerIT {

    private final String BASE_URL = "/email/sending-email";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc restEmailMockMvc;

    @Test
    @DisplayName("AssociadoController - Teste do endpoint de criação de associado")
    public void postCreateAssociadoTest() throws Exception {
        EmailDTO emailDTO = EmailMock.emailDTOTest();
        restEmailMockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.ownerRef").value(emailDTO.getOwnerRef()))
                .andExpect(jsonPath("$.emailFrom").value(emailDTO.getEmailFrom()))
                .andExpect(jsonPath("$.emailTo").value(emailDTO.getEmailTo()))
                .andExpect(jsonPath("$.subject").value(emailDTO.getSubject()))
                .andExpect(jsonPath("$.text").value(emailDTO.getText()));
    }

}
