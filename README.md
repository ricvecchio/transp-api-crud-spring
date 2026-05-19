# Backend Spring Boot - CRUD - Sistema para Gestão de Clientes, Pedidos e Usuários 🖥️

Este projeto consiste em uma **API REST** desenvolvida em **Java 21 com Spring Boot 3.2.2**, responsável pela gestão de **clientes, pedidos e usuários**.
O sistema oferece autenticação e autorização via **JWT**, persistência de dados em **PostgreSQL**, recuperação de senha por e-mail, cache de consultas e endpoints administrativos para visualização de dashboards analíticos.

O objetivo principal é fornecer um backend robusto, seguro e escalável para integração com o frontend desenvolvido em Angular.

---

## 🚀 Funcionalidades Principais

- **Gestão de Clientes 🧑‍💼:** cadastro, atualização, exclusão, paginação, busca por trecho do nome/razão social/cpf-cnpj e exportação completa para backup.
- **Gestão de Pedidos 📦:** criação, edição, cancelamento lógico e listagem dos últimos pedidos por cliente.
- **Filtros avançados de pedidos 🔎:** consulta por cliente, intervalo de datas e status.
- **Controle de Usuários 👥:** login, registro, listagem paginada, consulta por username, atualização de permissão e exclusão.
- **Recuperação de senha ✉️:** geração de token JWT para reset e envio de link por e-mail.
- **Dashboard Analítico 📊:** top 5 clientes com maiores gastos por mês (por ano informado).
- **Health Check 🩺:** endpoint público para verificar disponibilidade do servidor.
- **Segurança 🔐:** Spring Security + JWT com controle por papéis (`USER`, `ADMIN`, `DESENV`).
- **CI/CD e deploy automatizado 🤖:** workflow GitHub Actions com publicação em VPS.

---

## 🧱 Arquitetura do Projeto

O projeto foi desenvolvido em arquitetura monolítica em camadas, seguindo o padrão MVC adaptado para API REST.

### Estrutura em camadas
- **Controller (Apresentação):** recebe requisições HTTP e retorna respostas.
- **Service (Negócio):** concentra regras de negócio, validações e tratamento de fluxo.
- **Repository (Persistência):** acesso e manipulação dos dados via Spring Data JPA.
- **Model/Entities (Domínio):** representa as tabelas e dados de negócio.

### Fluxo
➡️ Controller → Service → Repository → Banco de Dados

---

## 🛠 Tecnologias Utilizadas

### Backend
- Java 21 ☕
- Spring Boot 3.2.2 🍃
- Spring Security 🔐
- JWT (`com.auth0:java-jwt`) 🔑
- Spring Data JPA / Hibernate 🗄️
- Spring Validation
- Spring Mail
- Spring Cache
- Lombok 📝
- PostgreSQL 🐘
- Maven ⚙️
- Docker 🐳

### Frontend Integrado
- Angular 14 ⚡
- TypeScript 🧩
- HTML & CSS 🎨
- Angular Material UI 🖼️

### CI/CD e DevOps
- GitHub Actions 🤖 – deploy automatizado
- Deploy em VPS (systemd + JAR)

---

## 📌 Pré-requisitos

Antes de executar o projeto, certifique-se de ter:

- **Java 21**
- **Maven 3.9+**
- **PostgreSQL configurado**
- **Docker (opcional)**

---

## 📁 Estrutura do Projeto

- `src/main/java` – código-fonte principal da aplicação
- `src/main/resources` – configurações da aplicação (`application.properties`)
- `src/test` – testes automatizados
- `.github/workflows/deploy.yml` – pipeline de build e deploy

---

## 📂 Endpoints Principais

### ClienteController 🧑‍💼
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/clientes?page=0&pageSize=10&filter=` | Lista clientes com paginação e filtro | `USER/ADMIN/DESENV` |
| GET | `/api/clientes/backup` | Retorna todos os clientes sem paginação (exportação/backup) | `USER/ADMIN/DESENV` |
| GET | `/api/clientes/{idCliente}` | Busca cliente por ID | `USER/ADMIN/DESENV` |
| GET | `/api/clientes/trecho/{trechoBusca}` | Busca por trecho (nome/razão social/cpf-cnpj) | `USER/ADMIN/DESENV` |
| POST | `/api/clientes` | Cria cliente | `USER/ADMIN/DESENV` |
| PUT | `/api/clientes/{idCliente}` | Atualiza cliente | `USER/ADMIN/DESENV` |
| DELETE | `/api/clientes/{idCliente}` | Remove cliente | `USER/ADMIN/DESENV` |

### PedidoController 📦
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/pedidos?page=0&pageSize=10&clienteFiltro=&dataInicial=YYYY-MM-DD&dataFinal=YYYY-MM-DD&statusFiltro=` | Lista pedidos com filtros combinados | `USER/ADMIN/DESENV` |
| GET | `/api/pedidos/{idPedido}` | Busca pedido por ID | `USER/ADMIN/DESENV` |
| GET | `/api/pedidos/ultimos?idCliente=1&limite=3` | Lista últimos pedidos por cliente | `USER/ADMIN/DESENV` |
| POST | `/api/pedidos` | Cria pedido | `USER/ADMIN/DESENV` |
| PUT | `/api/pedidos/{idPedido}` | Atualiza pedido | `USER/ADMIN/DESENV` |
| DELETE | `/api/pedidos/{idPedido}` | Cancela pedido (status `Cancelado`) | `USER/ADMIN/DESENV` |

### UserController 👥
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/users/list?page=0&pageSize=10&filter=` | Lista paginada de usuários | `ADMIN/DESENV` |
| GET | `/api/users/{username}` | Busca usuário por username | Autenticado |
| PUT | `/api/users/{idUser}` | Atualiza permissão do usuário | Autenticado |
| DELETE | `/api/users/{idUser}` | Remove usuário | Autenticado |
| POST | `/api/users/login` | Autentica e retorna token JWT | Público |
| POST | `/api/users/register` | Registra novo usuário | Público |
| POST | `/api/users/recoverPassword` | Envia e-mail com link de recuperação de senha | Público |
| POST | `/api/users/resetPassword` | Redefine senha via token | Público |

### DashboardController 📊
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/dashboard?page=0&pageSize=10&filter=&year=2026` | Dashboard com total mensal + top 5 clientes por mês no ano informado | `ADMIN/DESENV` |

### HealthController 🩺
| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/health` | Verifica se o servidor está no ar — retorna `{ "status": "UP" }` | Público |

---

## 🔑 Autenticação

A autenticação é feita via JWT. Após login bem-sucedido, envie o token no cabeçalho das requisições protegidas:

```http
Authorization: Bearer <token>
```

---

## Segurança e Permissões

- Sessão stateless com filtro JWT (`SecurityFilter`).
- Endpoints públicos: `GET /api/health`, `POST /api/users/login`, `POST /api/users/register`, `POST /api/users/recoverPassword`, `POST /api/users/resetPassword`, `OPTIONS /**`, `/error`.
- Endpoints de `clientes` e `pedidos`: `USER`, `ADMIN` e `DESENV`.
- Endpoints `GET /api/users/list` e `GET /api/dashboard`: apenas `ADMIN` e `DESENV`.

---

## Configuração de Ambiente

### Perfis Spring
- `application.properties` ativa por padrão o perfil `local`.
- `application-local.properties` para desenvolvimento local.
- `application-prod.properties` para produção (com variáveis de ambiente e SSL na porta `8443`).

### Variáveis importantes
- Banco: `DB_URL_VPS`, `DB_USER_VPS`, `DB_PASSWORD`
- JWT: `SPRING_SECURITY_TOKEN`
- E-mail SMTP: `SPRING_MAIL_HOST`, `SPRING_MAIL_PORT`, `SPRING_MAIL_USERNAME`, `SPRING_MAIL_PASSWORD`
- SSL: `SPRING_SSL_PASSWORD`

---

## Configuração e Execução

### Local (Maven)
```sh
./mvnw spring-boot:run
```

### Build do JAR
```sh
./mvnw clean package
```

### Docker
```sh
docker build -t transp-api-crud-spring .
docker run --rm -p 8080:8080 transp-api-crud-spring
```

---

## CI/CD e Deploy

O workflow `/.github/workflows/deploy.yml` executa:

1. checkout do código;
2. setup Java 21;
3. build do JAR com Maven;
4. cópia do artefato para VPS via SSH;
5. restart do serviço systemd `transp-api-crud-spring`.

---

## Observações

- A API possui cache habilitado para consultas de clientes (`Clientes` e `ClientesBackup`), pedidos e usuários.
- O endpoint `GET /api/clientes/backup` retorna a lista completa de clientes sem paginação, com cache próprio (`ClientesBackup`), invalidado automaticamente em qualquer operação de escrita.
- O endpoint de recuperação de senha envia link para o frontend em `https://saotomecatimesaotomecatime.com/home/recuperar-senha`.
- O endpoint `GET /api/health` pode ser usado pelo frontend para validar conectividade com o servidor antes de exibir a interface.

---

👉 Repositório frontend: [transp-crud-angular](https://github.com/ricvecchio/transp-crud-angular)
