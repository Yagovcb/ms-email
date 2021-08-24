package br.com.bcbdigital.ms.email.adapters.inbound.controllers;

import br.com.bcbdigital.ms.email.application.domain.Email;
import br.com.bcbdigital.ms.email.application.domain.PageInfo;
import br.com.bcbdigital.ms.email.application.ports.EmailServicePort;
import br.com.bcbdigital.ms.email.adapters.dto.EmailDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EmailServicePort emailServicePort;

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
        return new ResponseEntity<>(emailServicePort.sendEmail(emailDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Endpoint que retorna todos os Emails ja enviados e registrados na base")
    @GetMapping(path ="/emails", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Email>> getAllEmails(@PageableDefault(page = 0, size = 5, sort = "emailId", direction = Sort.Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(emailServicePort.findAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Endpoint que retorna um email especifico dado seu Id")
    @GetMapping(path ="/emails/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getEmailById(@PathVariable(value="id") UUID emailId){
        Optional<Email> emailModelOptional = emailServicePort.findById(emailId);
        return emailModelOptional.<ResponseEntity<Object>>map(email ->
                ResponseEntity.status(HttpStatus.OK).body(email)).orElseGet(() ->
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found."));
    }
}
