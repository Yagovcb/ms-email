package br.com.bcbdigital.ms.email.adapters.outbound.persistence;

import br.com.bcbdigital.ms.email.adapters.outbound.persistence.entities.EmailEntity;
import br.com.bcbdigital.ms.email.application.domain.Email;
import br.com.bcbdigital.ms.email.application.domain.PageInfo;
import br.com.bcbdigital.ms.email.application.ports.EmailRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Primary
public class H2EmailRepository implements EmailRepositoryPort {

    private final SpringDataEmailRepository emailRepository;

    public H2EmailRepository(final SpringDataEmailRepository orderRepository) {
        this.emailRepository = orderRepository;
    }

    @Autowired
    ObjectMapper mapper;

    @Override
    public Email save(Email email) {
        EmailEntity emailEntity = emailRepository.save(mapper.convertValue(email, EmailEntity.class));
        return mapper.convertValue(emailEntity, Email.class);
    }

    @Override
    public List<Email> findAll(PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize());
        return emailRepository.findAll(pageable).stream().map(entity -> mapper.convertValue(entity, Email.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Email> findById(UUID emailId) {
        Optional<EmailEntity> emailEntity = emailRepository.findById(emailId);
        if (emailEntity.isPresent()) {
            return Optional.of(mapper.convertValue(emailEntity.get(), Email.class));
        } else {
            return Optional.empty();
        }
    }
}
