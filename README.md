
# Olá, eu sou o Yago! 👋


## Microsserviço de envio de emails

Nesse projeto, fiz um Microsserviço que faz o envio de emails utilizando o SpringMail.
Utilizando mensageria através do RabbitMQ e o SpringAMQP.
Também apliquei padrões de testes usando o JUnit para fornecer alguns testes unitarios e de integração.
Toda a documentação da API está descrita também no Swagger
## Authors

#### A idealizadora principal desse projeto é a grande desenvolvedora e arquiteta [@michellibrito](https://github.com/MichelliBrito)

E se quiserem saber um pouco mais sobre meus projetos é só clicar no link - [@yagovcb](https://www.github.com/Yagovcb)
## Preparações

Antes de usar a o codigo desse projeto, é necessario que seja realizada algumas configurações previas. Que pretendo repassar uma a uma.

### Para utilizar o RabbitMQ

Para iniciar a utilização do RabbitMQ no seu projeto, você precisa adicionar essa dependencia no seu pom.xml

```bash
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```
uma vez adicionada a dependencia, você precisa adicionar as informações abaixo no seu arquivo application.properties

```bash
spring.rabbitmq.addresses=[Endereço url da sua instancia]
spring.rabbitmq.queue=[Nome da fila que você quer criar]
```
Uma ultima informação adicional, é que utilizaremos uma instancia que podemos criar no site

```bash
https://www.cloudamqp.com/
```
Após finalizar o processo de criação de conta, crie uma instancia padrão e gratuita!

Ao final, será fornecido a você na tela, uma lista de propiedades que correspondem as configurações de sua instancia.
Para finalizar a configuração, você precisa copiar a informação que está na tela de detalhe da sua instancia com a indicação
"URL AMQP", após isso, cole na propiedade "addresses" do seu application.properties (citado acima)

Para finalizar a configuração, você deve criar duas classes. Uma no pacote conf e outra no pacote consumer

#### **Para o pacote config**

Crie basicamente com essa estrutura
```bash
    @Value("${spring.rabbitmq.queue}")
    private String queue;


    @Bean
    public Queue queue(){
        return new Queue(queue, true);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
```
E não se esqueça da anotação @Configuration, pra indicar pro Spring que trata-se de uma classe de configuração

#### **Para o pacote consumer**

Crie basicamente essa estrutura
```bash
    @Autowired
    private [Seu Service] service;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload [Seu DTO] dto){
        service.[Chamada para o seu metodo no service];
        log.info("Email enviado por: " + dto.getOwnerRef());
    }
```
Não se esqueça da anotação @Component, pois ela é fundamental para indicar pro spring que sua classe deve ficar "escutando" durante a execução do Microsserviço

### Para utilizar o SpringMail
Para iniciar a utilização do SpringMail no seu projeto, você precisa adicionar essa dependencia no seu pom.xml

```bash
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```
uma vez adicionada a dependencia, você precisa adicionar as informações abaixo no seu arquivo application.properties

```bash
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=[Seu email que será usado como base]
spring.mail.password=[Senha de 16 digitos]
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
Para gerar essa senha de 16 digitos você pode seguir o passo-a-passo abaixo
```bash
https://support.google.com/accounts/answer/185833
```
Uma vez finalizado, sua aplicação está pronta para utilziar o SpringMail

Se você ficou com alguma duvida, assista a aula da [@michellibrito](https://github.com/MichelliBrito) no link abaixo
https://www.youtube.com/watch?v=ZBleZzJf6ro
## API Reference

#### **Acesso a aplicação**

Para acessar a aplicação, após rodar o projeto, é necessario usar URL abaixo
```http
  http://localhost:8080
```

#### **Post - Envio de email**

```http
  POST /api/email/sending-email
```

| Parametros | Tipo     | Descrição                                                        |
| :--------  | :------- | :--------------------------------------------------------------- |
| `ownerRef` | `string` | **Required**. Referencia de quem está enviando o email           |
| `emailFrom`| `string` | **Required**. Email de origem (Mesmo da configuração SpringMail) |
| `emailTo`  | `string` | **Required**. Email de Destino                                   |
| `subject`  | `string` | **Required**. O assunto do Email                                 |
| `text`     | `string` | **Required**. O texto do Email em si                             |

Nesse endpoint, todas essas informações devem ser enviadas no body(corpo) da requisição.
De forma para garantir o funcionamento da aplicação.
Acompanhe o exemplo abaixo

```http
{
    "ownerRef": "Fulando", 
    "emailFrom":"fulano@gmail.com", 
    "emailTo": "Ciclando@gmail.com",
    "subject": "Teste API",
    "text": "Email de teste da API"
}
```
#### **Documentação de EndPoints**

A documentação dos endpoints, feita usando a linguagem de descrição de interface Swagger,
encontra-se no endereço URL

```http
  http://localhost:8080/api/swagger-ui/
```
#
#### **Persistencia de Dados**

A aplicação conta com um banco de dados em memoria, o H2, localizado na URL abaixo

```http
  http://localhost:8080/api/h2-console/login.jsp
```
E para acessar o console, é necessario utilizar os dados abaixo
| Usuario   | Senha    |
| :-------- | :------- |
| `admin`   | `root123`|

OBS.: Esse BD só pode ser acessado com a aplicação em funcionamento
## Tecnologias utilizadas

- Linguagem utilizada:
  ![Java](https://img.shields.io/badge/Java-ea2d2f?style=flat-square&logo=java&logoColor=ffffff)
- Framework e Ferramentas:
  ![SpringBoot](https://img.shields.io/badge/SpringBoot-33CC00?style=flat-square&logo=springboot&logoColor=ffffff)
  ![Swagger2](https://img.shields.io/badge/Swagger2-33AC7C?style=flat-square&logo=swagger&logoColor=ffffff)
  ![JUnit5](https://img.shields.io/badge/JUnit_5-336600?style=flat-square&logo=junit5&logoColor=ea2d2f)
  ![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FFFFDF?style=flat-square&logo=rabbitMQ&logoColor=FF6600)
- Bibliotecas:
  ![ModelMapper](https://img.shields.io/badge/ModelMapper-3333CC?style=flat-modelMapper&logo=modelMapper&logoColor=ffffff)
  ![Lombok](https://img.shields.io/badge/Lombok-663300?style=flat-square&logo=lombok&logoColor=ffffff)
## 🔗 Links
[![Contact](https://img.shields.io/badge/yago.vcb@gmail.com-FFFEAF?style=flat-square&logo=gmail&logoColor=red)](mailto:yago.vcb@hotmail.com)
[![Twitter](https://img.shields.io/badge/@Yagovcb-1DA1F2?style=flat-square&logo=twitter&logoColor=white)](https://twitter.com/Yagovcb)
[![Linkedin](https://img.shields.io/badge/Yago_do_Valle_Castelo_Branco-0077b5?style=flat-square&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/yagovcb/)
## Feedback

Se vocês tiverem algum feedback para me dar, porfavor é só mandar um email para: yago.vcb@gmail.com


## Appendix

Caros amigos, sintam-se a vontade para criar PR's com melhorias ou acrescimos para esse Projeto!

  