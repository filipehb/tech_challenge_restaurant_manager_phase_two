# Restaurant Manager — Tech Challenge Phase 02 / Fase 02

**Language / Idioma:** [English](#english) | [Português](#português)

![Coverage](.github/badges/jacoco.svg)

---

# English

Restaurant management system, developed as the deliverable for **Phase 02** of the Tech Challenge (FIAP / Pós Tech).

In this phase, the focus is managing **user types**, **restaurants**, and **menu items**, with code organized under **Clean Architecture**, a documented REST API, automated tests, and execution via **Docker Compose**.

## Table of contents

- [Context and goal](#context-and-goal)
- [Features](#features)
- [Architecture](#architecture)
- [Technology stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [How to run](#how-to-run)
- [API endpoints](#api-endpoints)
- [Postman collection](#postman-collection)
- [Tests](#tests)
- [OpenAPI documentation](#openapi-documentation)
- [Repository structure](#repository-structure)

---

## Context and goal

A group of restaurants hired the development of a single shared system to reduce the cost of individual solutions. Delivery is done in phases.

**Goal of this phase:** expand the system with:

1. User type management (e.g., Restaurant Owner and Customer) and association with users
2. Full restaurant registration
3. Menu item registration
4. Clean code practices, documentation, tests, and Docker infrastructure

---

## Features

### User type
- User type CRUD
- Field: **type name**
- Association of an existing user with a user type

### Restaurant
CRUD with the fields:
- Name
- Address
- Kitchen type
- Opening hours
- Restaurant owner (responsible user)
- Association of menu items with the restaurant

### Menu item
CRUD with the fields:
- Name
- Description
- Price
- Available only on site (`availableOnlyOnSite`)
- Photo path (`image`)

---

## Architecture

The project follows **Clean Architecture**, separating business rules from technical details (HTTP, JPA, Docker).

```text
infra/web (REST API, JSON, Spring config)
        ↓
core/controllers + usecases + domain + gateway (interfaces)
        ↓
infra/database (entities, repositories, gateway implementations)
        ↓
PostgreSQL
```

### Layers

| Layer | Package | Responsibility |
|---|---|---|
| **Domain** | `core.domain` | Entities and business rules |
| **Application** | `core.usecases`, `core.controllers`, `core.dto`, `core.gateway` | Use cases, orchestration, and persistence contracts |
| **Infrastructure — Web** | `infra.web` | REST controllers, JSON, HTTP mappers, beans (`CoreConfig`) |
| **Infrastructure — Database** | `infra.database` | JPA entities, repositories, mappers, and gateway implementations |

### Main principle

- The **core** does not depend on Spring Web or JPA
- The **API** calls core controllers/use cases
- The **gateways** (interfaces in core) are implemented in infrastructure

Example flow (associate menu item → restaurant):

1. `RestaurantApiController` receives the HTTP request
2. `RestaurantController` (core) orchestrates
3. `AssociateMenuItemUseCase` applies the rule
4. `RestaurantGateway` / `MenuItemGateway` load and persist
5. `RestaurantGatewayImpl` (JPA) saves to PostgreSQL

---

## Technology stack

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

## Prerequisites

- JDK 21+
- Docker and Docker Compose

---

## How to run

```bash
docker compose up --build
```

Services:

| Service | URL / port |
|---|---|
| Application | http://localhost:8080 |
| PostgreSQL | `localhost:7433` |

Database credentials (Compose):

- Database: `tech_challenge_restaurant_manager`
- User: `postgres`
- Password: `root`

## API endpoints

Base URL: `http://localhost:8080`

### User Type — `/api/v1/user-type`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/user-type` | Create user type |
| `GET` | `/api/v1/user-type/{userTypeId}` | Get user type |
| `PUT` | `/api/v1/user-type/{userTypeId}` | Update user type |
| `DELETE` | `/api/v1/user-type/{userTypeId}` | Delete user type |

Example body:

```json
{
  "name": "Restaurant Owner"
}
```

### User — `/api/v1/user`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/user` | Create user |
| `POST` | `/api/v1/user/associate-user/{userId}/to-type/{userTypeId}` | Associate user with a type |

Example body (creation):

```json
{
  "email": "owner@restaurant.com",
  "password": "SecurePass123",
  "name": "Maria Owner"
}
```

### Restaurant — `/api/v1/restaurants`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/restaurants` | Create restaurant |
| `GET` | `/api/v1/restaurants/{restaurantId}` | Get restaurant |
| `PUT` | `/api/v1/restaurants/{restaurantId}` | Update restaurant |
| `DELETE` | `/api/v1/restaurants/{restaurantId}` | Delete restaurant |
| `POST` | `/api/v1/restaurants/associate-menu-item/{menuItemId}/to-restaurant/{restaurantId}` | Associate item with restaurant |

Kitchen types (`typeKitchen`):

- `COMMERCIAL`
- `INDUSTRIAL`
- `GHOST_KITCHEN`
- `FAST_CASUAL`
- `FOOD_TRUCK`

Example body:

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

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/v1/menu` | Create menu item |
| `GET` | `/api/v1/menu/{menuItemId}` | Get item |
| `PUT` | `/api/v1/menu/{menuItemId}` | Update item |
| `DELETE` | `/api/v1/menu/{menuItemId}` | Delete item |

Example body:

```json
{
  "name": "Feijoada",
  "price": 45.90,
  "description": "Complete feijoada with side dishes",
  "image": "/images/feijoada.jpg",
  "availableOnlyOnSite": false
}
```

## Postman collection

Files under:

```text
src/main/resources/postman/
├── Restaurant_Manager_Phase_Two.postman_collection.json
└── Restaurant_Manager_Local.postman_environment.json
```

How to use:

1. Open Postman
2. Import the collection and the environment
3. Select the **Restaurant Manager - Local** environment
4. Run the requests in folder order (1 → 4)

---

## Tests

Run all tests:

```bash
./gradlew test
```

---

## OpenAPI documentation

With the application running, interactive docs are available via SpringDoc (Swagger UI), usually at:

- Swagger UI: http://localhost:8080/swagger-ui.html  
- OpenAPI JSON: http://localhost:8080/v3/api-docs  

---

## Repository structure

```text
src/main/java/.../
├── core/                         # Business rules (Clean Architecture)
│   ├── controllers/
│   ├── domain/
│   ├── dto/
│   ├── exceptions/
│   ├── gateway/
│   ├── mappers/
│   └── usecases/
└── infra/                        # Technical details
    ├── database/                 # JPA, repositories, gateways
    └── web/                      # REST API, JSON, config
src/main/resources/
├── application.properties
├── db/migration/                 # Flyway
└── postman/                      # Test collections
Dockerfile
compose.yaml                      # App + PostgreSQL
```

---

# Português

Sistema de gestão de restaurantes, desenvolvido como entrega da **Fase 02** do Tech Challenge (FIAP / Pós Tech).

Nesta fase, o foco é a gestão de **tipos de usuário**, **restaurantes** e **itens de cardápio**, com código organizado em **Clean Architecture**, API REST documentada, testes automatizados e execução via **Docker Compose**.

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
