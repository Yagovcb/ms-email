package br.com.bcbdigital.ms.email.services.dto;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 *  Classe da entidade {@link EmailDTO}
 *
 *  @author Yago Castelo Branco
 *
 * @since 21/08/2021
 * */
@Data
public class EmailDTO {

    @NotBlank
    private String ownerRef;

    @NotBlank
    @Email
    private String emailFrom;

    @NotBlank
    @Email
    private String emailTo;

    @NotBlank
    private String subject;

    @NotBlank
    private String text;
}
