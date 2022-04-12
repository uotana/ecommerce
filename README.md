# ecommerce

Este projeto consiste em uma API Rest que fornece funcionalidades para o gerenciamento de fabricantes, produtos, usuarios e seus respectivos carrinhos de compras dentro de um ambiente e-commerce.

### Usuários

#### Criando usuário
[POST] `http://localhost:8080/v1/users/create`

Deve ser passado no corpo da requisição o formato como no exemplo a seguir:

```
{
	"name" : "String",
	"birth_date" : "LocalDate",
	"username" : "String",
	"password" : "Bcrypt*"
}
```
> * Obs :  O valor do atributo "password" deve ser criptografado no formato Bcrypt.

#### Listando todos os usuário

[GET] `http://localhost:8080/v1/users`

#### Listar um usuário

[GET] `http://localhost:8080/v1/users/{id}`

#### Editando um usuário

[PUT] `http://localhost:8080/v1/users/{id}`

> Deve ser informado um corpo no seguinte formato:

```
{
	"name" : "String",
	"birth_date" : "LocalDate",
	"username" : "String",
	"password" : "Bcrypt*"
}
```

#### Deletando um registro de usuário

[DELETE] `http://localhost:8080/v1/users/{id}`

### Autenticação

[POST] `http://localhost:8080/v1/auth`

{
	"username" : "String",
	"password" : "String"
}

> Obs: No ato da autenticação, o formato da senha deve ser passado sem criptografia.

### Fabricantes

#### Adicionando fabricante

[POST] `http://localhost:8080/v1/manufacturers`

```
{
	"name" : "String"
}

```

### Listando fabricantes

[GET] `http://localhost:8080/v1/manufacturers`

### Deletando fabricante

[DELETE] `http://localhost:8080/v1/manufacturers/{id}`

### Produtos

#### Adicionando um produto

[POST] `http://localhost:8080/v1/products`

> Deve ser passado um corpo no seguinte formato:

```
{
    "name":"String",
    "description":"String",
    "value": BigDecimal,
    "bar_code":"String",
    "id_manufacturer": Long,
    "weight":"Integer",
    "weight_measuring_unit":"String"
}
```
#### Listando todos os produtos

[GET] `http://localhost:8080/v1/products`

#### Listando um único produto

[GET] `http://localhost:8080/v1/products/{id}`

#### Alterando um registro de produto

[PUT] `http://localhost:8080/v1/products/{id}`

> Deve ser passado no corpo da requisição o seguinte formato:

```
{
    "name":"String",
    "description":"String",
    "value": BigDecimal,
    "bar_code":"String",
    "id_manufacturer": Long,
    "weight":"Integer",
    "weight_measuring_unit":"String"
}

```
#### Deletando um produto

[DELETE] `http://localhost:8080/v1/products/{id}`

### Carrinho de Compras

#### Adicionando produtos no carrinho

[POST] `http://localhost:8080/v1/carts`

> Deve ser passado no corpo da requisição o seguinte formato:

```
{
	"cart_id" : "1",
	"product_id" : "6"
}
```

### Deletando produtos do carrinho

[DELETE] `http://localhost:8080/v1/carts`

> O corpo da requisição deve conter o formato a seguir:

```
{
	"cart_id" : "1",
	"product_id" : "6"
}
```
