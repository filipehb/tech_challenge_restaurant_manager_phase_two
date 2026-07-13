# Restaurant Manager — Tech Challenge Fase 02

Sistema de gestão de restaurantes, desenvolvido como entrega da **Fase 02** do Tech Challenge (FIAP / Pós Tech).

Nesta fase, o foco é a gestão de **tipos de usuário**, **restaurantes** e **itens de cardápio**, com código organizado em **Clean Architecture**, API REST documentada, testes automatizados e execução via **Docker Compose**.

---

## Sumário

- [Contexto e objetivo](#contexto-e-objetivo)
- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Stack tecnológica](#stack-tecnológica)
- [Pré-requisitos](#pré-requisitos)
- [Como executar](#como-executar)
- [Endpoints da API](#endpoints-da-api)
- [Collection Postman](#collection-postman)
- [Testes](#testes)
- [Documentação OpenAPI](#documentação-openapi)
- [Estrutura do repositório](#estrutura-do-repositório)
- [Entregáveis da Fase 02](#entregáveis-da-fase-02)

---

## Contexto e objetivo

Um grupo de restaurantes contratou o desenvolvimento de um sistema único e compartilhado para reduzir o custo de soluções individuais. A entrega é feita em fases.

**Objetivo desta fase:** expandir o sistema com:

1. Gestão de tipos de usuário (ex.: Dono de Restaurante e Cliente) e associação a usuários
2. Cadastro completo de restaurantes
3. Cadastro de itens do cardápio
4. Boas práticas de código limpo, documentação, testes e infraestrutura Docker

---

## Funcionalidades

### Tipo de usuário
- CRUD de tipo de usuário
- Campo: **nome do tipo**
- Associação de um usuário existente a um tipo de usuário

### Restaurante
CRUD com os campos:
- Nome
- Endereço
- Tipo de cozinha
- Horário de funcionamento
- Dono do restaurante (usuário responsável)
- Associação de itens de cardápio ao restaurante

### Item do cardápio
CRUD com os campos:
- Nome
- Descrição
- Preço
- Disponibilidade apenas no restaurante (`availableOnlyOnSite`)
- Caminho da foto do prato (`image`)

---

## Arquitetura

O projeto segue **Clean Architecture**, separando regras de negócio de detalhes técnicos (HTTP, JPA, Docker).

```text
infra/web (API REST, JSON, config Spring)
        ↓
core/controllers + usecases + domain + gateway (interfaces)
        ↓
infra/database (entities, repositories, gateway implementations)
        ↓
PostgreSQL
```

### Camadas

| Camada | Pacote | Responsabilidade |
|---|---|---|
| **Domain** | `core.domain` | Entidades e regras de negócio |
| **Application** | `core.usecases`, `core.controllers`, `core.dto`, `core.gateway` | Casos de uso, orquestração e contratos de persistência |
| **Infrastructure — Web** | `infra.web` | Controllers REST, JSON, mappers HTTP, beans (`CoreConfig`) |
| **Infrastructure — Database** | `infra.database` | Entities JPA, repositories, mappers e implementações dos gateways |

### Princípio principal

- O **core** não depende de Spring Web nem de JPA
- A **API** chama controllers/use cases do core
- Os **gateways** (interfaces no core) são implementados na infraestrutura

Exemplo de fluxo (associação menu item → restaurante):

1. `RestaurantApiController` recebe o HTTP
2. `RestaurantController` (core) orquestra
3. `AssociateMenuItemUseCase` aplica a regra
4. `RestaurantGateway` / `MenuItemGateway` carregam e persistem
5. `RestaurantGatewayImpl` (JPA) salva no PostgreSQL

---

## Stack tecnológica

- Java **21**
- Spring Boot **4**
- Spring Web / Spring Data JPA
- PostgreSQL
- Flyway
- SpringDoc OpenAPI
- Docker / Docker Compose (`compose.yaml`)
- Gradle
- JUnit + Mockito + Testcontainers

---

## Pré-requisitos

- JDK 21+
- Docker e Docker Compose

---

## Como executar

```bash
docker compose up --build
```

Serviços:

| Serviço | URL / porta |
|---|---|
| Aplicação | http://localhost:8080 |
| PostgreSQL | `localhost:7433` |

Credenciais do banco (Compose):

- Database: `tech_challenge_restaurant_manager`
- User: `postgres`
- Password: `root`

## Endpoints da API

Base URL: `http://localhost:8080`

### User Type — `/api/v1/user-type`

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/v1/user-type` | Cria tipo de usuário |
| `GET` | `/api/v1/user-type/{userTypeId}` | Busca tipo de usuário |
| `PUT` | `/api/v1/user-type/{userTypeId}` | Atualiza tipo de usuário |
| `DELETE` | `/api/v1/user-type/{userTypeId}` | Remove tipo de usuário |

Exemplo de body:

```json
{
  "name": "Dono de Restaurante"
}
```

### User — `/api/v1/user`

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/v1/user` | Cria usuário |
| `POST` | `/api/v1/user/associate-user/{userId}/to-type/{userTypeId}` | Associa usuário a um tipo |

Exemplo de body (criação):

```json
{
  "email": "owner@restaurant.com",
  "password": "SecurePass123",
  "name": "Maria Owner"
}
```

### Restaurant — `/api/v1/restaurants`

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/v1/restaurants` | Cria restaurante |
| `GET` | `/api/v1/restaurants/{restaurantId}` | Busca restaurante |
| `PUT` | `/api/v1/restaurants/{restaurantId}` | Atualiza restaurante |
| `DELETE` | `/api/v1/restaurants/{restaurantId}` | Remove restaurante |
| `POST` | `/api/v1/restaurants/associate-menu-item/{menuItemId}/to-restaurant/{restaurantId}` | Associa item ao restaurante |

Tipos de cozinha (`typeKitchen`):

- `COMMERCIAL`
- `INDUSTRIAL`
- `GHOST_KITCHEN`
- `FAST_CASUAL`
- `FOOD_TRUCK`

Exemplo de body:

```json
{
  "name": "Cantina da Maria",
  "address": "Rua das Flores, 100",
  "typeKitchen": "FAST_CASUAL",
  "restaurantSchedule": {
    "weeklyHours": {
      "MONDAY": [
        { "startHour": "08:00:00", "endHour": "12:00:00" },
        { "startHour": "14:00:00", "endHour": "18:00:00" }
      ],
      "FRIDAY": [
        { "startHour": "08:00:00", "endHour": "22:00:00" }
      ]
    }
  },
  "owner": 1,
  "menuItems": []
}
```

### Menu Item — `/api/v1/menu`

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/v1/menu` | Cria item do cardápio |
| `GET` | `/api/v1/menu/{menuItemId}` | Busca item |
| `PUT` | `/api/v1/menu/{menuItemId}` | Atualiza item |
| `DELETE` | `/api/v1/menu/{menuItemId}` | Remove item |

Exemplo de body:

```json
{
  "name": "Feijoada",
  "price": 45.90,
  "description": "Feijoada completa com acompanhamentos",
  "image": "/images/feijoada.jpg",
  "availableOnlyOnSite": false
}
```

### Ordem sugerida de teste

1. Criar tipo de usuário  
2. Criar usuário  
3. Associar usuário ao tipo  
4. Criar restaurante (usando o `owner` do usuário)  
5. Criar item de cardápio  
6. Associar item ao restaurante  
7. Consultar / atualizar / excluir conforme necessário  

---

## Collection Postman

Arquivos em:

```text
src/main/resources/postman/
├── Restaurant_Manager_Phase_Two.postman_collection.json
└── Restaurant_Manager_Local.postman_environment.json
```

Como usar:

1. Abra o Postman
2. Importe a collection e o environment
3. Selecione o environment **Restaurant Manager - Local**
4. Execute os requests na ordem das pastas (1 → 4)

A collection inclui scripts que salvam IDs (`userTypeId`, `userId`, `restaurantId`, `menuItemId`) automaticamente para os próximos requests.

---

## Testes

Executar todos os testes:

```bash
./gradlew test
```

---

## Documentação OpenAPI

Com a aplicação no ar, a documentação interativa fica disponível via SpringDoc (Swagger UI), normalmente em:

- Swagger UI: http://localhost:8080/swagger-ui.html  
- OpenAPI JSON: http://localhost:8080/v3/api-docs  

---

## Estrutura do repositório

```text
src/main/java/.../
├── core/                         # Regras de negócio (Clean Architecture)
│   ├── controllers/
│   ├── domain/
│   ├── dto/
│   ├── exceptions/
│   ├── gateway/
│   ├── mappers/
│   └── usecases/
└── infra/                        # Detalhes técnicos
    ├── database/                 # JPA, repositories, gateways
    └── web/                      # API REST, JSON, config
src/main/resources/
├── application.properties
├── db/migration/                 # Flyway
└── postman/                      # Collections de teste
Dockerfile
compose.yaml                      # App + PostgreSQL
```
