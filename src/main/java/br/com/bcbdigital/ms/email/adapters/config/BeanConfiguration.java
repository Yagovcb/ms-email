package br.com.bcbdigital.ms.email.adapters.config;

import br.com.bcbdigital.ms.email.MsEmailApplication;
import br.com.bcbdigital.ms.email.application.ports.EmailRepositoryPort;
import br.com.bcbdigital.ms.email.application.ports.SendEmailServicePort;
import br.com.bcbdigital.ms.email.application.services.EmailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = MsEmailApplication.class)
public class BeanConfiguration {

    @Bean
    EmailServiceImpl emailService(EmailRepositoryPort emailRepositoryPort, SendEmailServicePort sendEmailServicePort){
        return new EmailServiceImpl(emailRepositoryPort, sendEmailServicePort);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
