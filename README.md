# 🚚 ERP Logística API — Backend Restful

[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=flat-square&logo=openjdk)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=flat-square&logo=spring)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Security-JWT-blue?style=flat-square&logo=json-web-tokens)](https://jwt.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Neon.tech-blue?style=flat-square&logo=postgresql)](https://neon.tech)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=flat-square&logo=docker)](https://www.docker.com/)

API RESTful para gestão de logística e transportes, desenvolvida para gerenciar com segurança o ciclo de vida completo de encomendas, desde a entrada nos marketplaces, passando pela roteirização de entregas, até o acompanhamento de ocorrências no *last-mile*.

---

## 🎯 Principais Funcionalidades

- **Gestão de Cargas & Pacotes:** Cadastro, atualização de status e controle individual de encomendas.
- **Roteirização & Romaneio:** Agrupamento de pacotes por veículo e motorista responsável.
- **Rastreamento Last-Mile:** Registro e histórico de ocorrências (tentativas de entrega, ausência do destinatário, avarias, etc.).
- **Dashboard Operacional:** Endpoint com métricas e contadores em tempo real sobre o volume e status das operações.
- **Filtros Dinâmicos e Paginação:** Consultas otimizadas com filtros por datas, motoristas e status de carga via Spring Data `Pageable`.
- **Tratamento Global de Exceções:** Respostas HTTP padronizadas com `@RestControllerAdvice`.

---

## 🔒 Segurança e Autenticação

A API utiliza arquitetura de segurança **Stateless** com **Spring Security** e **JWT (JSON Web Token)**:

- **Hash de Senhas:** Criptografia irreversível via `BCrypt`.
- **Controle de Acesso Baseado em Perfis (RBAC):**
  - `ROLE_ADMIN`: Acesso total às rotas de gestão e relatórios.
  - `ROLE_OPERADOR`: Criação e manipulação de cargas e rotas.
  - `ROLE_MOTORISTA`: Atualização de status e registro de ocorrências durante o percurso.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21+
- **Framework:** Spring Boot 3+ (Spring Data JPA, Spring Security, Spring Validation, Spring Web)
- **Banco de Dados:** PostgreSQL (Serverless via Neon)
- **Autenticação:** Java JWT (Auth0)
- **Documentação da API:** OpenAPI 3 / Swagger UI
- **Testes Automatizados:** JUnit 5, Mockito
- **Build & Dependências:** Maven

---

## 📋 Endpoints Principais (API)

| Módulo | Método | Endpoint | Descrição |
| :--- | :--- | :--- | :--- |
| **Autenticação** | `POST` | `/api/auth/registrar` | Cadastro de novos usuários |
| **Autenticação** | `POST` | `/api/auth/login` | Autenticação e geração do Token JWT |
| **Pacotes** | `GET` | `/api/pacotes` | Listagem paginada de pacotes com filtros |
| **Pacotes** | `POST` | `/api/pacotes` | Cadastro de nova encomenda |
| **Pacotes** | `POST` | `/api/pacotes/{id}/ocorrencias` | Registro de ocorrência no last-mile |
| **Rotas** | `POST` | `/api/rotas` | Criação de novo romaneio de entrega |
| **Rotas** | `PUT` | `/api/rotas/{id}/finalizar` | Baixa completa na rota e pacotes associados |
| **Dashboard** | `GET` | `/api/dashboard/resumo` | Métricas gerais da operação logística |

> 💡 **Documentação Interativa:** Com a aplicação rodando, acesse `http://localhost:8080/swagger-ui/index.html` para explorar e testar todos os endpoints.

---

## 🚀 Como Executar o Projeto Localmente

### Pré-requisitos
- **Java 21** ou superior instalado
- **Maven** configurado (ou utilize o wrapper `./mvnw`)
- Git

### Passos

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/SEU_USUARIO/erp-logistica-api.git](https://github.com/SEU_USUARIO/erp-logistica-api.git)
   cd erp-logistica-api
   ```

2. **Configure o Banco de Dados:**
   Ajuste as credenciais do PostgreSQL no arquivo `src/main/resources/application.properties` ou via variáveis de ambiente.

3. **Execute a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```
   A aplicação estará disponível em `http://localhost:8080`.

---

## 🐳 Executando via Docker

Caso prefira rodar a aplicação em containers sem precisar instalar dependências locais:

```bash
docker compose up --build
```

---

## 🧪 Testes Automatizados

O projeto conta com uma suíte de testes unitários para validar as regras de negócio e a camada de segurança.

Para executar os testes via terminal:

```bash
./mvnw test
```

---