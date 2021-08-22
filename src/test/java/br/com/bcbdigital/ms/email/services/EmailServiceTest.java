package br.com.bcbdigital.ms.email.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.bcbdigital.ms.email.mock.EmailMock;
import br.com.bcbdigital.ms.email.model.Email;
import br.com.bcbdigital.ms.email.model.enums.StatusEmail;
import br.com.bcbdigital.ms.email.repository.EmailRepository;
import br.com.bcbdigital.ms.email.services.dto.EmailDTO;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmailService.class})
@ExtendWith(SpringExtension.class)
@DisplayName("EmailServiceTest - Teste da classe EmailService")
public class EmailServiceTest {

    @MockBean
    private EmailRepository emailRepository;

    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    @DisplayName("EmailServiceTest - Teste de criacao e envio de um Email")
    public void testSendEmail() throws MailException {
        doNothing().when(this.javaMailSender).send((org.springframework.mail.SimpleMailMessage) any());

        Email email = new Email();
        email.setText("Text");
        email.setSubject("Hello from the Dreaming Spires");
        email.setStatusEmail(StatusEmail.SENT);
        email.setId(UUID.randomUUID());
        email.setSendDateEmail(LocalDateTime.of(1, 1, 1, 1, 1));
        email.setEmailFrom("jane.doe@example.org");
        email.setEmailTo("jane.doe@example.org");
        email.setOwnerRef("Owner Ref");
        when(this.emailRepository.save((Email) any())).thenReturn(email);

        EmailDTO emailDTO = EmailMock.emailDTOTest();
        assertSame(email, this.emailService.sendEmail(emailDTO));
        verify(this.javaMailSender).send((org.springframework.mail.SimpleMailMessage) any());
        verify(this.emailRepository).save((Email) any());
    }

    @Test
    @DisplayName("EmailServiceTest - Teste de criacao e envio de um Email forçando o erro MailAuthenticationException")
    public void testSendEmail_erroMailAuthenticationException() throws MailException {
        doNothing().when(this.javaMailSender).send((org.springframework.mail.SimpleMailMessage) any());

        Email email = new Email();
        email.setText("Text");
        email.setSubject("Hello from the Dreaming Spires");
        email.setStatusEmail(StatusEmail.SENT);
        email.setId(UUID.randomUUID());
        email.setSendDateEmail(LocalDateTime.of(1, 1, 1, 1, 1));
        email.setEmailFrom("jane.doe@example.org");
        email.setEmailTo("jane.doe@example.org");
        email.setOwnerRef("Owner Ref");
        when(this.emailRepository.saveAndFlush((Email) any())).thenReturn(email);
        when(this.emailRepository.save((Email) any())).thenThrow(new MailAuthenticationException("Msg"));

        EmailDTO emailDTO = EmailMock.emailDTOTest();
        assertSame(email, this.emailService.sendEmail(emailDTO));
        verify(this.javaMailSender).send((org.springframework.mail.SimpleMailMessage) any());
        verify(this.emailRepository).save((Email) any());
        verify(this.emailRepository).saveAndFlush((Email) any());
    }

}

