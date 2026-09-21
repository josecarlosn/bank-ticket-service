# Bank ticket Service   <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-original.svg" width="45" alt="Java"/>



 
![Arquitetura do projeto](https://github.com/josecarlosn/bank-ticket-service/blob/main/image.jpeg)
API de gerenciamento de senhas de atendimento (estilo SAC) para agências bancárias, com filas por categoria, prioridade e atualização em tempo real via **WebSocket**.
 
> Projeto de portfólio — modela um cenário real de fila bancária (Caixa/Atendimento, prioridade legal, múltiplos guichês).
## Stack

![Java 21](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge\&logo=springboot\&logoColor=white)
![PostgreSQL 16](https://img.shields.io/badge/PostgreSQL_16-4169E1?style=for-the-badge\&logo=postgresql\&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge\&logo=flyway\&logoColor=white)
![WebSocket STOMP](https://img.shields.io/badge/WebSocket_STOMP-010101?style=for-the-badge\&logo=socketdotio\&logoColor=white)




**Java 21 · Spring Boot · Spring Data JPA · Hibernate · PostgreSQL · Flyway · WebSocket · OpenAPI · Lombok · Maven · JUnit 5 · Mockito**
## Pré-requisitos
 
Para rodar o projeto localmente é necessário ter instalado:
 
- **JDK 21** (o Maven Wrapper `./mvnw` cuida do Maven em si, mas não do JDK)
- **Docker + Docker Compose**, com o serviço do Docker ativo (para subir o banco Postgres)
## Como funciona
 
- Totem gera senha → atendente chama para um guichê → atendimento finaliza (ou é cancelado).
- Duas categorias: **Caixa** (`C`/`PC`) e **Atendimento** (`A`/`PA`), cada uma com fila normal e prioritária.
- Código da senha (ex: `PA0003`) é composto em tempo de leitura, nunca salvo pronto no banco.
- Numeração usa uma tabela de controle dedicada (`ticket_counts`), evitando race condition do clássico `MAX() + 1`.
## Arquitetura
 
Controller → Service → Entity (Rich Domain) → Repository, com DTOs de entrada/saída.
 
As regras de transição do `Ticket` (`call`, `finish`, `cancel`) vivem na própria Entity, garantindo que a validação nunca seja "esquecida" em algum ponto de chamada.
 
## Endpoints principais
 
|| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/tickets` | Lista todos os tickets |
| `POST` | `/tickets` | Cria um novo ticket |
| `POST` | `/tickets/{id}/call` | Chama o ticket para um guichê |
| `POST` | `/tickets/{id}/finish` | Finaliza o atendimento |
| `POST` | `/tickets/{id}/cancel` | Cancela o ticket |
| `GET` | `/departments` | Lista categorias ativas |
| `GET` | `/departments/all` | Lista todas as categorias (ativas e inativas) |
| `POST` | `/departments` | Cria uma categoria |
| `PUT` | `/departments/{id}` | Atualiza nome, tag ou tag de prioridade |
| `DELETE` | `/departments/{id}` | Remove a categoria (só se não houver guichês vinculados) |
| `PATCH` | `/departments/activate/{id}` | Ativa a categoria e todos os seus guichês |
| `PATCH` | `/departments/deactivate/{id}` | Desativa a categoria e todos os seus guichês |
| `GET` | `/desks` | Lista guichês |
| `POST` | `/desks` | Cria um guichê |
| `PUT` | `/desks/{id}` | Atualiza número ou categoria do guichê |
| `PATCH` | `/desks/activate/{id}` | Ativa o guichê |
| `PATCH` | `/desks/deactivate/{id}` | Desativa o guichê |
| `GET` | `/ticket-counts` | Lista os contadores de senha por categoria/dia |
| `POST` | `/ticket-counts` | Cria ou incrementa um contador |
| `DELETE` | `/ticket-counts/{id}` | Remove um contador |
 
## Tempo real (WebSocket)
 
Dois canais separados: `/topic/tickets/panel` (painel público, dados enxutos) e `/topic/tickets/desk-view` (tela do atendente, dados completos) — evitando expor informação interna no painel público.
 
## Como rodar o projeto
 
**1. Subir o banco de dados:**
```bash
docker compose up -d
```
 
**2. Configurar `application.properties`** (já deve estar assim no repo):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5000/bank-ticket
spring.datasource.username=postgres
spring.datasource.password=bank-ticket-password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=validate
```
 
**3. Rodar a aplicação** (Flyway aplica as migrations automaticamente no start):
```bash
./mvnw spring-boot:run
```
 
A API sobe por padrão em `http://localhost:8080`.
 
> Se a porta `8080` já estiver em uso, defina `server.port=<outra_porta>` no `application.properties`.
 
## Testes
 
Testes unitários da camada de service com **JUnit 5 + Mockito** (`@ExtendWith(MockitoExtension.class)`), sem subir o contexto Spring nem depender do banco: repositories e `SimpMessagingTemplate` são mockados.
 
Ficam em `src/test/java/br/com/josecarlosn/bank_ticket_service/service/`:
 
| Classe de teste | O que cobre |
|---|---|
| `DepartmentServiceTest` | Listagem, criação (nome/tag/tag de prioridade duplicados), atualização parcial, exclusão com guichês vinculados, ativar/desativar em cascata |
| `DeskServiceTest` | Listagem, criação (guichê duplicado, categoria inexistente), atualização, ativar/desativar |
| `TicketCountServiceTest` | Listagem, criação/incremento do contador, validação de data futura, próximo número da senha, exclusão |
| `TicketServiceTest` | Geração de senha (código normal e prioritário), chamar, finalizar, cancelar e notificação nos canais WebSocket |
 
**Rodar todos os testes:**
```bash
./mvnw test
```
 
**Rodar uma classe específica:**
```bash
./mvnw test -Dtest=TicketServiceTest
```
 
## Migrations (Flyway)
 
- Localizadas em `src/main/resources/db/migration/`
- Nomenclatura: `V<versão>__<descricao>.sql`
- Regra: migration aplicada nunca é editada — qualquer ajuste vira uma nova migration
- `ddl-auto=validate`: o Hibernate não cria/altera schema, só confere se as entidades batem com o que o Flyway já aplicou
