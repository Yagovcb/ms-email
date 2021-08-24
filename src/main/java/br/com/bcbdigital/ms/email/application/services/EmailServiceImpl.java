package br.com.bcbdigital.ms.email.application.services;

import br.com.bcbdigital.ms.email.adapters.dto.EmailDTO;
import br.com.bcbdigital.ms.email.application.domain.Email;
import br.com.bcbdigital.ms.email.application.domain.PageInfo;
import br.com.bcbdigital.ms.email.application.domain.enums.StatusEmail;
import br.com.bcbdigital.ms.email.application.ports.EmailRepositoryPort;
import br.com.bcbdigital.ms.email.application.ports.EmailServicePort;
import br.com.bcbdigital.ms.email.application.ports.SendEmailServicePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class EmailServiceImpl implements EmailServicePort {

    private final ObjectMapper mapper = new ObjectMapper();
    private final EmailRepositoryPort emailRepositoryPort;
    private final SendEmailServicePort sendEmailServicePort;

    public EmailServiceImpl(final EmailRepositoryPort emailRepositoryPort, final SendEmailServicePort sendEmailServicePort) {
        this.emailRepositoryPort = emailRepositoryPort;
        this.sendEmailServicePort = sendEmailServicePort;
    }

    /**
     * Método responsavel por salvar e enviar um {@link Email} especifico, dado seu {@link EmailDTO}
     *
     * @param dto entidade {@link EmailDTO} que será persistida e enviado
     *
     * @throws Exception Caso não seja possivel enviar o {@link Email}
     * @return um {@link Email} já persistido no banco e enviado para o destinatario
     * */
    @Override
    public Email sendEmail(EmailDTO dto) {
        Email email = this.mapper.convertValue(dto, Email.class);
        email.setSendDateEmail(LocalDateTime.now());
        try{
            log.info("EmailService - Iniciando o processo de envio do email para o destinatario");
            sendEmailServicePort.sendEmailSmtp(email);
            email.setStatusEmail(StatusEmail.SENT);
        } catch (Exception e){
            email.setStatusEmail(StatusEmail.ERROR);
        } finally {
            log.info("EmailService - Iniciando o processo de persistencia de um novo email");
            return save(email);
        }
    }

    @Override
    public PageImpl<Email> findAll(Pageable pageable) {
        PageInfo pageInfo = mapper.convertValue(pageable, PageInfo.class);
        List<Email> emailList = emailRepositoryPort.findAll(pageInfo);
        return new PageImpl<Email>(emailList, pageable, emailList.size());
    }

    @Override
    public Optional<Email> findById(UUID emailId) {
        //inserir manipulação de dados/regras
        return emailRepositoryPort.findById(emailId);
    }

    @Override
    public Email save(Email email) {
        return emailRepositoryPort.save(email);
    }
}
