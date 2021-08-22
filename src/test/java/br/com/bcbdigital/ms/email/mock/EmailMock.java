package br.com.bcbdigital.ms.email.mock;

import br.com.bcbdigital.ms.email.services.dto.EmailDTO;

public class EmailMock {

    public static EmailDTO emailDTOTest(){
        EmailDTO dto = new EmailDTO();
        dto.setOwnerRef("Joãozinho");
        dto.setEmailFrom("bronzecastelob@gmail.com");
        dto.setEmailTo("yago.vcb@gmail.com");
        dto.setSubject("Email pra ambiente de teste");
        dto.setText("Corpo do email de teste no ambiente");
        return dto;
    }
}
