Weather API

REST API desenvolvida em Java/Spring Boot que recebe uma cidade, consulta a Open-Meteo, obtém os dados de localização e do tempo atual e persiste o resultado em PostgreSQL.

Tecnologias

Java 21

Spring Boot 3.1.3

Spring Web

Spring Data JPA / Hibernate

PostgreSQL 15+ (Docker)

Maven

Open-Meteo Geocoding API

Open-Meteo Forecast API

Postman (para testes)

Pré-requisitos

Java 21 instalado

Maven instalado

Docker Desktop / Docker Engine em execução

PostgreSQL com Docker

Criar e iniciar o container:

docker run --name pg-docker \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=local \
  -p 5432:5432 \
  -d postgres

Se o container já existir:

docker start pg-docker

Configuração

src/main/resources/application.properties

spring.application.name=weather-api

spring.datasource.url=jdbc:postgresql://localhost:5432/local
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080

As tabelas são criadas/atualizadas automaticamente pelo Hibernate.

Executar a aplicação

Na raiz do projeto:

mvn clean package
mvn spring-boot:run


A API fica disponível em:

http://localhost:8080

Endpoints

Criar previsão por cidade

POST /api/weather?city=Maputo

Exemplo:

POST http://localhost:8080/api/weather?city=Maputo

Não é necessário enviar Body.

Listar previsões

GET /api/weather

Buscar por ID

GET /api/weather/{id}

Exemplo:

GET http://localhost:8080/api/weather/1

Apagar por ID

DELETE /api/weather/{id}

Exemplo:

DELETE http://localhost:8080/api/weather/1

Dados retornados

A resposta do POST/GET contém:

id - identificador do registro na base de dados

locationId - identificador da localização na Open-Meteo

name - cidade

latitude / longitude - coordenadas

country - país

countryCode - código do país

timezone - fuso horário

currentTime - hora atual

temperature - temperatura atual

Fluxo da aplicação

Cliente/Postman
      ↓
WeatherController
      ↓
WeatherService
      ↓
OpenMeteoClient
      ↓
Open-Meteo
      ↓
Weather Entity
      ↓
WeatherRepository (JPA/Hibernate)
      ↓
PostgreSQL

Verificar dados no PostgreSQL

docker exec -it pg-docker psql -U postgres -d local

Depois:

SELECT * FROM weather;

Para sair:

\q
