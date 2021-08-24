package br.com.bcbdigital.ms.email.application.ports;

import br.com.bcbdigital.ms.email.adapters.dto.EmailDTO;
import br.com.bcbdigital.ms.email.application.domain.Email;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EmailServicePort {

    Email sendEmail(EmailDTO dto);
    PageImpl<Email> findAll(Pageable pageable);
    Optional<Email> findById(UUID emailId);
    Email save(Email email);

}
