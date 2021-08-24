package br.com.bcbdigital.ms.email.adapters.inbound.consumers;

import br.com.bcbdigital.ms.email.adapters.dto.EmailDTO;
import br.com.bcbdigital.ms.email.application.ports.EmailServicePort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 *  Classe da consummer de {@link EmailDTO} para ficar onvindo as requisições da fila
 *
 *  @author Yago Castelo Branco
 *
 * @since 22/08/2021
 * */
@Slf4j
@Component
public class EmailConsumer {

    @Autowired
    EmailServicePort servicePort;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload EmailDTO dto){
        servicePort.sendEmail(dto);
        log.info("Email enviado por: " + dto.getOwnerRef());
    }
}
