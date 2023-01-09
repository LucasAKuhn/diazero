# diazero

Projeto conta com uma classe de Incident, com os seguintes campos:
     - Long idIncident
     - String name
     - String description
     - LocalDateTime createdAt
     - LocalDateTime updatedAt
     - LocalDateTime closedAt

IncidentService conta com todos metodos de CRUD, e um metodo que lista os últimos 20 incidentes ordenados por ordem decrescente.

Dentro da pasta test, criei testes unitários para os metodos CRUD de repository, e service.

#IMPORTANTE
Uma copia das requisições do POSTMAN foram anexas dentro da pasta resources. 
Nas requisições é possível testar todos os retornos dos metodos.