package br.com.bcbdigital.ms.email.controller;

import br.com.bcbdigital.ms.email.model.Email;
import br.com.bcbdigital.ms.email.services.EmailService;
import br.com.bcbdigital.ms.email.services.dto.EmailDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

/**
 *  REST controller para gerenciar {@link Email}.
 *  Criado por Yago Castelo Branco
 *
 * @since 21/08/2021
 * */
@Slf4j
@RepositoryRestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService service;

    /**
     * {@code POST /sending-email} : Rest Endpoint de Criação do {@link Email}
     * @param emailDTO passado no corpo da requisição
     * @return o {@link ResponseEntity} com o status {@code 201 (CREATED)} e a entidade {@link Email}
     * criada e enviada ao destinatario
     * */
    @ApiOperation(value = "Endpoint de criação e envio de um Email. Recebe no corpo da requisição dto do email que será enviado e persistido na base")
    @PostMapping(path = "/sending-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Email> sendingEmail(@RequestBody @Valid EmailDTO emailDTO){
        log.info("EmailController - Iniciando o processo do envio do email: " + emailDTO);
        return new ResponseEntity<>(service.sendEmail(emailDTO), HttpStatus.CREATED);
    }
}
