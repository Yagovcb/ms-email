package br.com.bcbdigital.ms.email.application.ports;

import br.com.bcbdigital.ms.email.application.domain.Email;

public interface SendEmailServicePort {

    void sendEmailSmtp(Email email);
}
