# Instruções para execução do projeto:

 Clonar o projeto e Inserir o clientId e clientSecret nas variaveis de ambiente:
 
    HUBSPOT_CLIENT_SECRET=8fe7d409-449d-4d97-a8a8-47b09e954e9d
    HUBSPOT_CLIENT_ID=e532b447-4c74-466f-b6a6-dddeedb0d9e1
Acessar via web a seguinte URL e autorizar o aplicativo: http://localhost:8080/oauth/authorize
## Para testar a criação de contatos:
Utilizando Postman ou qualquer outra ferramenta de teste de APIs:  
Utilizar o método POST para a seguinte URL: http://localhost:8080/create-contact  
Utilizar a seguinte estrutura no body:

     {
     "email": "teste@teste.com",
     "firstName": "Teste",
     "lastName": "Teste",
     "phone": "99999999999"   
     }
   
## Para testar o WebHook:
> **Nota:** Não ficou muito claro qual a função real e o HubSpot não permite a chamada de WebHooks em ambientes locais.

  Utilizar o método POST para a seguinte URL: http://localhost:8080/webhook/contact  
  Utilizar a seguinte estrutura no body:
    
    [
      {
        "eventId": 123456,
        "subscriptionId": 78910,
        "portalId": 1234567,
        "appId": 456789,
        "occurredAt": 1660752815000,
        "objectId": 999888,
        "propertyName": "email",
        "propertyValue": "teste@teste.com",
        "changeSource": "CRM",
        "eventType": "contact.propertyChange",
        "objectType": "contact"
      }
    ]

## Decisões Técnicas:
**Spring Boot:** Framework robusto e com excelente suporte para implementação de API's REST.  
**Caffeine:** Utilizado para definir um tempo limite de cache, muito util para questões de segurança.  
**Resilience4j:** Utilizado para definição do rate limit solicitado no case através da anotação @RateLimiter  

**Possíveis melhorias:** Aplicação de testes unitários e de integração visando uma maior confiabilidade no resultado final e implementação do mecanismo de "refresh" do token de acesso.
