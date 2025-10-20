## 👨‍💻 Autores

Leonardo Salazar - 557484

Alexsandro Macedo - 557068

Tiago Augusto - 558485

## Deploy

Site com deploy:

# Acessos

Para logar como **OPERADOR** utilize:

E-mail: operador@gmail.com

Senha: 123123ab

Para logar como **ADMIN** utilize:

E-mail: admin@gmail.com

Senha: 123123ab

# Imagens do sistema

<img width="963" height="568" alt="image" src="https://github.com/user-attachments/assets/58475b17-abc4-4136-883e-ff7028977463" />
<img width="1010" height="888" alt="image" src="https://github.com/user-attachments/assets/f48b08f4-3491-439d-b41c-1e3036b56061" />
<img width="1118" height="540" alt="image" src="https://github.com/user-attachments/assets/02644d62-857f-4f41-9048-a02b57e5b207" />
<img width="1202" height="509" alt="image" src="https://github.com/user-attachments/assets/9b2b9b23-d0e8-4f6f-8dd3-21ac1deb7288" />
<img width="842" height="962" alt="image" src="https://github.com/user-attachments/assets/4d6854c2-7dfc-4ed4-b238-f55944ae246e" />
<img width="1192" height="523" alt="image" src="https://github.com/user-attachments/assets/961b8241-aa92-4b8c-b285-b3e6206d9007" />
<img width="1165" height="598" alt="image" src="https://github.com/user-attachments/assets/cf1d184b-4576-486a-8468-c7c3355bd46b" />

# Performance Kids 🎮⚽

## 📌 Descrição do Projeto
O **Performance Kids** é um sistema em **Spring Boot 3 (Java 17)** para gerenciamento de **brinquedos esportivos** voltados a crianças até 12 anos.  
A aplicação expõe uma API REST com **CRUD completo** de **Brinquedos**, **Categorias** e **Funcionários**, usando **DTOs**, **validações**, **HATEOAS** e documentação via **Swagger OpenAPI**. Além disso contamos com sistema de funcionarios separado por permissões, a aba **Funcionários** pode ser acessada exclusivamente por **ADMIN's**, já as abas **Categorias** e **Brinquedos** podem ser acessadas normalmente pelos **Operadores**

- **IDE utilizada:** IntelliJ IDEA (pode ser executado também no Eclipse/NetBeans).
- **Porta padrão:** `8081`

---

## 🏗️ Tecnologias & Dependências
- Spring Boot 3 (Web, Data JPA, Validation, HATEOAS)
- Lombok
- Oracle JDBC Driver
- Springdoc OpenAPI (Swagger UI)

---

## ⚙️ Configuração (`application.properties`)
```properties
server.port=8081

spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XEPDB1
spring.datasource.username=PERFORMANCE_KIDS
spring.datasource.password=senha_aqui
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

> Use `ddl-auto=validate` em produção. O schema Oracle pode ser criado com um usuário dedicado (`PERFORMANCE_KIDS`).

---

## 📂 Estrutura de Pacotes
```
br.com.fiap.performancekids
 ┣ assembler        → ModelAssemblers (HATEOAS)
 ┣ controller       → REST Controllers (Brinquedo, Categoria, Funcionario, Auth)
 ┣ dto              → DTOs
 ┣ entity           → Entidades JPA
 ┣ repository       → Repositórios JPA
 ┣ service          → Regras de negócio
 ┣ templates        → Templates do Thymeleaf para exibição das páginas
 ┗ PerformanceKidsApplication.java
```

---

## 🔑 Modelo de Dados (resumo)
### Brinquedo
- `id: Long`
- `nome: String`
- `categoria: Categoria (ManyToOne)`
- `classificacao: String` (`3-5`, `6-8`, `9-12`)
- `tamanho: String` (depende da categoria)
- `preco: BigDecimal`

### Categoria
- `id: Long`
- `nome: String` (`BOLA`, `TENIS`, `MEIA`, `ROUPA`, `RAQUETE`, `ACESSORIO`)
- `descricao: String` (opcional)

### Funcionario
- `id: Long`
- `nome: String`
- `email: String` (único)
- `senha: String`
- `cargo: String` (`ADMIN`, `OPERADOR`, ...)

---

## 🧠 Regras de Validação
- **Classificação** deve ser `3-5`, `6-8` ou `9-12`.
- **Categoria × Tamanho:**
  - `BOLA` → tamanho `3|4|5`
  - `ROUPA`/`MEIA` → `PP|P|M|G`
  - `TENIS` → `28..36`
- **Preço** `> 0`

---

## 🌐 Endpoints (CRUD) — com DTO & HATEOAS

### 1) Brinquedos
**POST `/brinquedos`** — cria
```json
{
  "nome": "Bola de Futebol Infantil",
  "categoriaId": 1,
  "classificacao": "6-8",
  "tamanho": "4",
  "preco": 79.90
}
```
**Resposta 201**
```json
{
  "id": 1,
  "nome": "Bola de Futebol Infantil",
  "categoriaId": 1,
  "categoriaNome": "BOLA",
  "classificacao": "6-8",
  "tamanho": "4",
  "preco": 79.90,
  "_links": {
    "self": { "href": "http://localhost:8081/brinquedos/1" },
    "lista": { "href": "http://localhost:8081/brinquedos" },
    "atualizar": { "href": "http://localhost:8081/brinquedos/1" },
    "deletar": { "href": "http://localhost:8081/brinquedos/1" }
  }
}
```

**GET `/brinquedos`** — lista (CollectionModel<EntityModel<DTO>>)

**GET `/brinquedos/{id}`** — busca por ID (EntityModel<DTO>)

**PUT `/brinquedos/{id}`** — atualiza
```json
{
  "nome": "Bola de Futebol Kids",
  "categoriaId": 1,
  "classificacao": "6-8",
  "tamanho": "4",
  "preco": 89.90
}
```

**DELETE `/brinquedos/{id}`** — remove (204 No Content)

---

### 2) Categorias
**POST `/categorias`**
```json
{
  "nome": "BOLA",
  "descricao": "Bolas esportivas para crianças"
}
```

**GET `/categorias`**, **GET `/categorias/{id}`**, **PUT `/categorias/{id}`**, **DELETE `/categorias/{id}`**

---

### 3) Funcionários
**POST `/funcionarios`**
```json
{
  "nome": "Ana Souza",
  "email": "ana@performancekids.com",
  "senha": "12345678",
  "cargo": "OPERADOR"
}
```

**GET `/funcionarios`**, **GET `/funcionarios/{id}`**, **PUT `/funcionarios/{id}`**, **DELETE `/funcionarios/{id}`**

---

## 🔗 HATEOAS — como usamos
- Controllers retornam **`EntityModel<DTO>`** (um recurso com `_links`)
- Listagens retornam **`CollectionModel<EntityModel<DTO>>`**
- Os links são montados por **Assemblers** (`BrinquedoModelAssembler`, etc.) com:
```java
linkTo(methodOn(BrinquedoController.class).buscarPorId(dto.getId())).withSelfRel();
```

---

## 🧪 Exemplos via cURL
**Criar Categoria**
```bash
curl -X POST http://localhost:8081/categorias \
 -H "Content-Type: application/json" \
 -d '{"nome":"BOLA","descricao":"Bolas esportivas"}'
```

**Criar Brinquedo**
```bash
curl -X POST http://localhost:8081/brinquedos \
 -H "Content-Type: application/json" \
 -d '{"nome":"Bola de Futebol Infantil","categoriaId":1,"classificacao":"6-8","tamanho":"4","preco":79.90}'
```

**Listar Brinquedos**
```bash
curl http://localhost:8081/brinquedos
```

**Atualizar Brinquedo**
```bash
curl -X PUT http://localhost:8081/brinquedos/1 \
 -H "Content-Type: application/json" \
 -d '{"id":1,"nome":"Bola Kids Pro","categoriaId":1,"classificacao":"6-8","tamanho":"4","preco":89.90}'
```

**Deletar Brinquedo**
```bash
curl -X DELETE http://localhost:8081/brinquedos/1
```

---

## 📖 Swagger (OpenAPI)
Acesse a documentação em:
```
http://localhost:8081/swagger-ui.html
```

---

## ▶️ Como executar
1. **Configurar** `application.properties` com seu Oracle.
2. **Rodar** pelo IntelliJ: `BrinquedosRevisaoApplication` (ou `PerformanceKidsApplication`), ou via Maven:
   ```bash
   mvn spring-boot:run
   ```
3. Testar no Postman/Insomnia ou pelo Swagger UI.

---

## 📝 Observações
- O campo **categoriaId** é obrigatório ao criar/atualizar Brinquedos.
- Regra de coerência **categoria × tamanho** e **classificação** já é validada no Service.
- Em produção, proteger endpoints e não expor senha em DTOs (já adotado no `FuncionarioDTO`).






