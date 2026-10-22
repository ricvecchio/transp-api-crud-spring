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

Campos novos (Cliente e Pedido, String; legado `valorAjudante`/`valorAdicional` mantido): `valorAjudanteVuc`, `valorAjudanteToco`, `adicionalMangueira`, `adicionalBombaGas`; no Pedido também `ajudanteToco` e `bombaGas` (`SIM`/`NÃO`). Script de produção: `src/main/resources/sql/2026-add-valores-ajudante-adicional.sql`.

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

## Preços para entregas aos sábados

Cliente e Pedido aceitam e retornam `precoCx5Sabado`, `precoCx10Sabado`,
`precoCx15Sabado`, `precoLv5Sabado`, `precoLv10Sabado` e `precoLv15Sabado`
nos endpoints POST, PUT, GET e listagens, incluindo o backup de clientes.
São strings de moeda, armazenadas em `VARCHAR(12) NOT NULL`, como os preços
existentes. Campos novos omitidos ou enviados como `null` são gravados e
retornados como `""`, inclusive no PUT (não é atualização parcial).

Acrescente este bloco ao payload completo de criação ou atualização de Cliente
ou Pedido, mantendo os demais campos obrigatórios:

```json
{
  "precoCx5Sabado": "100,00",
  "precoCx10Sabado": "200,00",
  "precoCx15Sabado": "300,00",
  "precoLv5Sabado": "110,00",
  "precoLv10Sabado": "220,00",
  "precoLv15Sabado": "330,00"
}
```

Um [payload completo de Cliente](src/test/resources/cliente-precos-sabado.json)
está disponível como exemplo. Para criar um Pedido a partir de um Cliente,
o frontend continua copiando os dados retornados pelo GET do Cliente para o
payload do Pedido, incluindo os seis preços, `idCliente` e os demais campos
próprios de Pedido. Não há consulta automática de preços pelo `idCliente`:
o Pedido preserva o snapshot enviado, independentemente de alterações futuras
no Cliente.

### Atualização do PostgreSQL

O projeto não usa Flyway/Liquibase. O perfil local usa `ddl-auto=update`,
mas produção usa `ddl-auto=none`. **Execute o script antes de iniciar a nova
versão**, também em bases locais que já tenham registros:

```sh
psql "$DATABASE_URL" -X -v ON_ERROR_STOP=1 \
  -f src/main/resources/db/scripts/20261021_precos_sabado.sql
```

Use uma URL de conexão aceita pelo `psql` (não a URL JDBC), apontando para a
base correta de homologação ou produção. Faça backup antes da execução.
O script transacional adiciona as doze colunas em `clientes` e `pedidos`,
preenche registros existentes com `""` e mantém esse valor como padrão para
inserções de versões antigas. Pode ser reexecutado sem recriar colunas.
Reinicie a aplicação após a atualização para renovar os caches.

Para conferir a estrutura:

```sql
SELECT table_name, column_name, data_type, character_maximum_length,
       is_nullable, column_default
FROM information_schema.columns
WHERE table_schema = 'public'
  AND table_name IN ('clientes', 'pedidos')
  AND column_name IN (
      'preco_cx5_sabado', 'preco_cx10_sabado', 'preco_cx15_sabado',
      'preco_lv5_sabado', 'preco_lv10_sabado', 'preco_lv15_sabado'
  )
ORDER BY table_name, column_name;
```

O resultado deve conter 12 linhas, com tamanho `12` e `is_nullable = 'NO'`.

### Validação

```sh
# Java 21 e Maven instalados; testes usam H2 isolado, sem acessar o banco local.
mvn -Dtest=PrecosSabadoMapperTest,PrecosSabadoIntegrationTest test
mvn clean package
```

Para validar manualmente em homologação, com um JWT autorizado: crie um Cliente
com o payload completo do exemplo; consulte seu ID; copie os dados para um
Pedido e acrescente seus campos obrigatórios; consulte o Pedido; altere os seis
preços via PUT e confira os GETs, as listagens e `/api/clientes/backup`.
Repita com os campos omitidos ou nulos para confirmar o retorno `""`.

---

## Opções salvas no Pedido

`entregaSabado` e `exibirPreco` são strings `"SIM"`/`"NÃO"` independentes de
`ajudante` e `adicional`. São persistidas em `pedidos.entrega_sabado` e
`pedidos.exibir_preco` (`VARCHAR(10) NOT NULL`) e retornadas no POST, PUT,
GET `/api/pedidos/{idPedido}`, listagem paginada e `/api/pedidos/ultimos`.
Ao editar um pedido emitido, o frontend deve usar esses valores retornados,
sem recalculá-los a partir dos preços ou do cadastro atual do Cliente.

Antes (payload antigo, trecho):

```json
{
  "ajudante": "SIM",
  "adicional": "NÃO"
}
```

Depois (acrescentar ao payload completo de criação/atualização):

```json
{
  "ajudante": "SIM",
  "adicional": "NÃO",
  "entregaSabado": "SIM",
  "exibirPreco": "NÃO"
}
```

Campos novos omitidos ou `null` recebem `"NÃO"`, inclusive no PUT, que
continua sendo atualização completa. Registros antigos também recebem
`"NÃO"`; não é possível reconstruir a seleção histórica que não foi salva.

Execute o script antes do deploy, após backup, em cada banco existente
(inclusive local, antes do JPA auto update):

```sh
psql "$DATABASE_URL" -X -v ON_ERROR_STOP=1 \
  -f src/main/resources/db/scripts/20261021_opcoes_pedido.sql
```

O script é transacional e reexecutável: adiciona as duas colunas, corrige
nulos e define o padrão `"NÃO"` com restrição `NOT NULL`. Reinicie a API
para renovar os caches. Os testes de mapper (`PedidoOpcoesMapperTest`) e
integração (`PrecosSabadoIntegrationTest`) cobrem as quatro combinações,
POST/PUT/GET/listagens, campos omitidos/nulos e preservação de ajudante/adicional.

---

## Observações

- A API possui cache habilitado para consultas de clientes (`Clientes` e `ClientesBackup`), pedidos e usuários.
- O endpoint `GET /api/clientes/backup` retorna a lista completa de clientes sem paginação, com cache próprio (`ClientesBackup`), invalidado automaticamente em qualquer operação de escrita.
- O endpoint de recuperação de senha envia link para o frontend em `https://saotomecatimesaotomecatime.com/home/recuperar-senha`.
- O endpoint `GET /api/health` pode ser usado pelo frontend para validar conectividade com o servidor antes de exibir a interface.

---

👉 Repositório frontend: [transp-crud-angular](https://github.com/ricvecchio/transp-crud-angular)
