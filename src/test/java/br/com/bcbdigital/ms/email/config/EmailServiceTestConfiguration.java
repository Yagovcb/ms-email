package br.com.bcbdigital.ms.email.config;

import br.com.bcbdigital.ms.email.repository.EmailRepository;
import br.com.bcbdigital.ms.email.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EmailServiceTestConfiguration {

    @Autowired
    private EmailRepository repository;

    @Bean
    public EmailService emailService(){ return new EmailService(repository); }
}
