package br.com.bcbdigital.ms.email.repository;

import br.com.bcbdigital.ms.email.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Classe Repository da entidade {@link Email}
 * @author  Yago Castelo Branco
 * @since 21/08/2021
 * */
@Repository
public interface EmailRepository extends JpaRepository<Email, UUID> {
}
