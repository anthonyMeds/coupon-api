# 💰 Coupon API

API responsável pelo gerenciamento de cupons de desconto, incluindo validação de código, regras de negócio e exclusão lógica (Soft Delete).

---

## 🚀 Tecnologias Utilizadas

A aplicação é construída utilizando o ecossistema Spring Boot, seguindo boas práticas de arquitetura e testes.

| Tecnologia               | Finalidade                                                        |
|-------------------------|------------------------------------------------------------------|
| **Spring Boot 4.0.0**   | Framework base para desenvolvimento da aplicação.                |
| **Java 21**             | Linguagem da aplicação.                                          |
| **Spring Data JPA**     | Persistência e abstração de acesso ao banco de dados.            |
| **H2 Database**         | Banco em memória utilizado para desenvolvimento e testes.        |
| **Springdoc OpenAPI**   | Geração automática da documentação Swagger UI.                   |
| **Lombok**              | Redução de boilerplate (getters, setters, construtores).         |
| **Spring Validation**   | Validações usando anotações (`@Valid`, `@NotNull`, etc.).        |
| **JUnit 5 + Mockito**   | Testes unitários da aplicação.                                   |

---

## ⚙️ Configuração e Execução Local

### 1. Pré-requisitos
- Java 21+
- Maven instalado

### 2. Clonar o projeto
```bash
git clone https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git
cd coupon-api
```

3. Rodar a aplicação
```bash
./mvnw spring-boot:run
```
O servidor iniciará na porta 8080.


# 🌐 Documentação e Ferramentas

| Ferramenta   | URL                               | Descrição                             |
|--------------|-----------------------------------|-----------------------------------------|
| Swagger UI   | http://localhost:8080/docs        | Interface interativa dos endpoints.     |

---

# 📑 Regras de Negócio Implementadas

## 🔹 Criação de Cupom (POST)

**Campos obrigatórios:**  
`code`, `description`, `discountValue`, `expirationDate`

### Regras do `code`:
- Pode conter caracteres especiais, mas **todos são removidos**.
- Após sanitização, deve ter **exatos 6 caracteres alfanuméricos**.

### Outras regras:
- `discountValue` deve ser **≥ 0.5**
- `expirationDate` deve ser **uma data futura**

### Status inicial ao criar:
- `status = ACTIVE`
- `redeemed = false`

---

## 🔹 Exclusão de Cupom (DELETE)

- Exclusão é **Soft Delete** → altera o status para `DELETED`
- **Não é permitido** excluir novamente um cupom já deletado

---

# 💻 Endpoints da API

**Base URL:** `/coupon`

---

## 1. ➕ Criar Cupom
**POST** `/coupon`  
Cria um novo cupom aplicando as validações e regras de negócio.

### Payload
```json
{
  "code": "ABC-123",
  "description": "Desconto de teste",
  "discountValue": 0.8,
  "expirationDate": "2025-11-04T17:14:45.180Z",
  "published": false
}

