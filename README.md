# API REST - CRUD - Sistema para gestão de clientes, emissão de pedidos e controle de usuários.

## Descrição
API REST desenvolvida em Java com Spring Boot 3.2.2 para a gestão de clientes, pedidos e usuários.

## Tecnologias Utilizadas
- Java 21
- Spring Boot 3.2.2
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT Authentication

## Dependências
- **Spring Boot**: 3.2.2
    - `spring-boot-starter-data-jpa`
    - `spring-boot-starter-jdbc`
    - `spring-boot-starter-web`
    - `spring-boot-starter-security`
    - `spring-boot-starter-validation`
    - `spring-boot-starter-actuator`
    - `spring-boot-starter-test`
- **Auth0 Java JWT**: 4.4.0
- **PostgreSQL Driver**: 42.2.5
- **Lombok**: 1.18.30
- **Jakarta Persistence API**: 3.1.0

## Endpoints

### ClienteController
| Método  | Endpoint                      | Descrição                                   |
|---------|--------------------------------|---------------------------------------------|
| GET     | `/api/clientes`               | Lista todos os clientes com paginação       |
| GET     | `/api/clientes/{idCliente}`    | Busca cliente por ID                        |
| GET     | `/api/clientes/trecho/{trechoBusca}` | Busca clientes por trecho do nome     |
| POST    | `/api/clientes`               | Cria um novo cliente                        |
| PUT     | `/api/clientes/{idCliente}`    | Atualiza um cliente existente               |
| DELETE  | `/api/clientes/{idCliente}`    | Deleta um cliente                           |

### PedidoController
| Método  | Endpoint                          | Descrição                            |
|---------|------------------------------------|--------------------------------------|
| GET     | `/api/pedidos`                    | Lista todos os pedidos com paginação |
| GET     | `/api/pedidos/{idPedido}`         | Busca pedido por ID                  |
| GET     | `/api/pedidos/ultimos`            | Lista os 3 últimos pedidos           |
| POST    | `/api/pedidos`                    | Cria um novo pedido                  |
| PUT     | `/api/pedidos/{idPedido}`         | Atualiza um pedido existente         |
| DELETE  | `/api/pedidos/{idPedido}`         | Cancela um pedido                    |

### UserController (Admin)
| Método  | Endpoint          | Descrição                                   |
|---------|-------------------|---------------------------------------------|
| GET     | `/api/users/list`     | Lista paginada de usuários                  |
| PUT     | `/api/users/{idUser}` | Atualiza um usuário                         |
| DELETE  | `/api/users/{idUser}` | Deleta um usuário                           |
| POST    | `/api/users/login`    | Autentica um usuário e gera um token JWT   |
| POST    | `/api/users/register` | Registra um novo usuário                    |

### DashboardController (Admin)
| Método  | Endpoint          | Descrição                                   |
|---------|-------------------|---------------------------------------------|
| GET     | `/api/dashboard` (Admin)  | Obtém dashboard do Top 5 de Clientes |

## Autenticação
A autenticação é feita via JWT (JSON Web Token). Após o login bem-sucedido, um token JWT é gerado e deve ser enviado no cabeçalho das requisições subsequentes:
```http
Authorization: Bearer <token>
```

## Segurança
A API utiliza Spring Security para autenticação e autorização. Os endpoints que requerem autenticação ou permissões específicas estão protegidos com anotações como @PreAuthorize.

## Configuração e Execução
1. Clone o repositório
2. Configure o banco de dados PostgreSQL
3. Atualize o `application.properties`
4. Execute o projeto com:
   ```sh
   mvn spring-boot:run
   ```
### Banco de Dados

A API utiliza PostgreSQL como banco de dados. Certifique-se de configurar as propriedades de conexão no arquivo `application.properties` ou `application.yml`.
