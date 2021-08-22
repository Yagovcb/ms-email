package br.com.bcbdigital.ms.email.model;

import br.com.bcbdigital.ms.email.model.enums.StatusEmail;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *  Classe da entidade {@link Email}
 *
 *  @author Yago Castelo Branco
 *
 * @since 21/08/2021
 * */
@Data
@Entity
@Table(name = "tb_email")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String ownerRef;

    private String emailFrom;

    private String emailTo;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String text;

    private LocalDateTime sendDateEmail;

    private StatusEmail statusEmail;

}
