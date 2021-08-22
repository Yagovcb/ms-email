package br.com.bcbdigital.ms.email.services;

import br.com.bcbdigital.ms.email.model.Email;
import br.com.bcbdigital.ms.email.model.enums.StatusEmail;
import br.com.bcbdigital.ms.email.repository.EmailRepository;
import br.com.bcbdigital.ms.email.services.dto.EmailDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 *  {@link Service} controller para gerenciar as  ações do controller {@link br.com.bcbdigital.ms.email.controller.EmailController}.
 *
 *  @author Yago Castelo Branco
 *
 *  @since 21/08/2021
 * */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class EmailService {

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private EmailRepository repository;

    @Autowired
    private JavaMailSender emailSender;

    /**
     * Construtora padrão usando a classe {@link EmailRepository}
     */
    public EmailService(EmailRepository repository) {
        this.repository = repository;
    }

    /**
     * Método responsavel por salvar e enviar um {@link Email} especifico, dado seu {@link EmailDTO}
     *
     * @param dto entidade {@link EmailDTO} que será persistida e enviado
     *
     * @throws MailException Caso não seja possivel enviar o {@link Email}
     * @return um {@link Email} já persistido no banco e enviado para o destinatario
     * */
    public Email sendEmail(EmailDTO dto){
        Email email = this.mapper.convertValue(dto, Email.class);
        email.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());
            log.info("EmailService - Iniciando o processo de envio do email para o destinatario");
            emailSender.send(message);
            email.setStatusEmail(StatusEmail.SENT);
            log.info("EmailService - Iniciando o processo de persistencia de um novo email");
            return repository.save(email);
        } catch (MailException e) {
            log.info("EmailService - Não foi possivel enviar o email para o destinatario");
            email.setStatusEmail(StatusEmail.ERROR);
            return repository.saveAndFlush(email);
        } finally {
            log.info("EmailService - O email foi persistido na base e enviado para o destinatario com sucesso!");
        }
    }
}
