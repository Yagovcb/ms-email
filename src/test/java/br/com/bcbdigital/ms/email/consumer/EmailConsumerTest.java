package br.com.bcbdigital.ms.email.consumer;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.bcbdigital.ms.email.mock.EmailMock;
import br.com.bcbdigital.ms.email.model.Email;
import br.com.bcbdigital.ms.email.model.enums.StatusEmail;
import br.com.bcbdigital.ms.email.services.EmailService;
import br.com.bcbdigital.ms.email.services.dto.EmailDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmailConsumer.class, EmailService.class})
@ExtendWith(SpringExtension.class)
@DisplayName("EmailConsummerTest - Teste da classe EmailConsummer")
public class EmailConsumerTest {
    @Autowired
    private EmailConsumer emailConsumer;

    @MockBean
    private EmailService emailService;

    @Test
    @DisplayName("EmailConsummerTest - Teste de escuta da classe EmailConsummer")
    public void testListen() {
        Email email = new Email();
        email.setText("Text");
        email.setSubject("Hello from the Dreaming Spires");
        email.setId(UUID.randomUUID());
        email.setStatusEmail(StatusEmail.SENT);
        email.setSendDateEmail(LocalDateTime.of(1, 1, 1, 1, 1));
        email.setEmailFrom("jane.doe@example.org");
        email.setEmailTo("jane.doe@example.org");
        email.setOwnerRef("Owner Ref");
        when(this.emailService.sendEmail((EmailDTO) any())).thenReturn(email);

        EmailDTO emailDTO = EmailMock.emailDTOTest();
        this.emailConsumer.listen(emailDTO);
        verify(this.emailService).sendEmail((EmailDTO) any());
    }
}

