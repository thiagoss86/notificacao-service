# Notificacao Service

Microserviço responsável pelo gerenciamento de notificações do Sistema de Gestão de Eventos.

O serviço foi extraído da aplicação principal `sistema-eventos` durante a Etapa 2 do projeto, utilizando uma arquitetura baseada em microserviços e comunicação síncrona via REST/OpenFeign.

## Tecnologias utilizadas

- Java 25
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- PostgreSQL
- Lombok
- Springdoc OpenAPI
- Maven

## Responsabilidade

O `notificacao-service` é responsável por:

- Criar notificações
- Listar notificações
- Buscar notificações por ID
- Buscar notificações por participante
- Buscar notificações por status
- Buscar notificações por tipo
- Alterar o status de uma notificação para `ENVIADA`
- Alterar o status de uma notificação para `FALHA`

Tipos de notificação:

- `EMAIL`
- `SISTEMA`

Status:

- `PENDENTE`
- `ENVIADA`
- `FALHA`

## API REST

A API está disponível por padrão na porta:

```text
http://localhost:8081
```

### Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/notificacoes` | Cria uma notificação |
| GET | `/notificacoes` | Lista todas as notificações |
| GET | `/notificacoes/{id}` | Busca uma notificação por ID |
| GET | `/notificacoes/buscar/participante/{id}` | Busca notificações por participante |
| GET | `/notificacoes/buscar/status?status=PENDENTE` | Busca por status |
| GET | `/notificacoes/buscar/tipo?tipo=EMAIL` | Busca por tipo |
| PATCH | `/notificacoes/{id}/enviada` | Marca como enviada |
| PATCH | `/notificacoes/{id}/falha` | Marca como falha |

## Swagger / OpenAPI

Com a aplicação em execução, a documentação da API pode ser acessada em:

```text
http://localhost:8081/swagger-ui/index.html
```

## Configuração do banco

Atualmente, durante a Etapa 2, o serviço utiliza o banco PostgreSQL `sistema_eventos`.

Configuração:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/sistema_eventos
    username: postgres
    password: SUA_SENHA
```

A separação do banco de dados por serviço será realizada na Etapa 3.

## Execução

Para executar o projeto localmente:

```bash
mvn spring-boot:run
```

Ou:

```bash
mvn clean package
java -jar target/notificacao-service-0.0.1-SNAPSHOT.jar
```

## Testes

Os testes automatizados podem ser executados com:

```bash
mvn clean test
```

A aplicação possui testes da API do `notificacao-service`, incluindo:

- Criação de notificações
- Busca por ID
- Alteração para `ENVIADA`
- Alteração para `FALHA`

## Arquitetura

Durante a Etapa 2, a responsabilidade de notificações foi extraída da aplicação monolítica `sistema-eventos` para este serviço independente.

A comunicação entre as aplicações ocorre através de HTTP utilizando OpenFeign:

```text
                    HTTP / OpenFeign
┌──────────────────┐ ───────────────────> ┌──────────────────────┐
│  sistema-eventos │                      │ notificacao-service  │
│                  │                      │                      │
│  Controller      │                      │ Controller           │
│       ↓          │                      │       ↓              │
│  Integration     │                      │ Service              │
│  Service         │                      │       ↓              │
│       ↓          │                      │ Repository           │
│  Feign Client    │                      │       ↓              │
└──────────────────┘                      │ PostgreSQL           │
                                          └──────────────────────┘
```

## Etapa 2

Nesta etapa foram implementados:

- Extração do módulo de notificações para um serviço independente
- API REST do `notificacao-service`
- DTOs para comunicação
- Documentação OpenAPI
- Comunicação entre serviços utilizando OpenFeign
- URL do serviço externalizada através de configuração
- Tratamento de indisponibilidade do serviço remoto
- Retorno HTTP `503 Service Unavailable`
- Testes automatizados
- Testes de comunicação através da aplicação principal

## Próximas etapas

Na Etapa 3 serão implementados:

- Profiles de configuração
- Variáveis de ambiente
- PostgreSQL independente para o serviço
- Configuração centralizada
- Dockerfile
- Docker Compose
