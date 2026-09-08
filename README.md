Weather API

API REST desenvolvida em Java com Spring Boot para consultar dados de uma cidade através da Open-Meteo e guardar as informações no PostgreSQL.

Tecnologias

Java 21
Spring Boot 3.1.3
Spring Web
Spring Data JPA
Hibernate
PostgreSQL
Docker
Maven
Open-Meteo API
Postman



A aplicação recebe o nome de uma cidade, consulta a Open-Meteo para obter os dados da localização e depois consulta os dados meteorológicos atuais.

As informações obtidas são guardadas no PostgreSQL.

Os dados principais são:

ID do registro
D da localização
Nome da cidade
Latitude
Longitude
País
Código do país
Timezone
Hora atual
Temperatura atual

PostgreSQL com Docker

O PostgreSQL é executado através de um container Docker.

Para criar o container:

bash
docker run --name pg-docker \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=local \
  -p 5432:5432 \
  -d postgres


para criar container:

bash
docker ps


se ja existe:

bash
docker start pg-docker


Configuração do banco

Configurações no ficheiro:

text
src/main/resources/application.properties


properties
spring.application.name=weather-api

spring.datasource.url=jdbc:postgresql://localhost:5432/local
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080


o hibernate atualiza as tabelas automaticamente

para executar:

Na raiz do projeto:

bash
mvn clean package


depois:

bash
mvn spring-boot:run


app esta em:

text
http://localhost:8080


Endpoints:

Criar uma previsão

http
POST /api/weather?city=Maputo


Exemplo:

text
http://localhost:8080/api/weather?city=Maputo


Não é necessário enviar Body.

Listar previsões

http
GET /api/weather


Buscar uma previsão por ID

http
GET /api/weather/{id}


Exemplo:

text
http://localhost:8080/api/weather/1


Apagar uma previsão

http
DELETE /api/weather/{id}


Exemplo:

text
http://localhost:8080/api/weather/1


Exemplo de resposta

json
{
  "id": 1,
  "locationId": 1040652,
  "name": "Maputo",
  "latitude": -25.96553,
  "longitude": 32.58322,
  "country": "Mozambique",
  "countryCode": "MZ",
  "timezone": "Africa/Maputo",
  "currentTime": "2026-09-08T13:30",
  "temperature": 22.4
}


Fluxo da aplicação

text
Postman
   |
   v
WeatherController
   |
   v
WeatherService
   |
   v
OpenMeteoClient
   |
   v
Open-Meteo
   |
   v
Weather
   |
   v
WeatherRepository
   |
   v
JPA / Hibernate
   |
   v
PostgreSQL


Estrutura principal

text
src/main/java/org/example
├── client
│   └── OpenMeteoClient.java
├── controller
│   └── WeatherController.java
├── entity
│   └── Weather.java
├── repository
│   └── WeatherRepository.java
├── service
│   └── WeatherService.java
└── Main.java


Verificar os dados no PostgreSQL

Para entrar no PostgreSQL dentro do container:

bash
docker exec -it pg-docker psql -U postgres -d local


Depois:


SELECT * FROM weather;


Para sair:

sql
\q

