# ecommerce-api
E-commerce API utilizando Spring Boot e JWT

Build Status

[![Build Status](https://travis-ci.org/luizimcpi/ecommerce-api.svg?branch=master)](https://travis-ci.org/luizimcpi/ecommerce-api)

Configurações de DB
```
Criar "e-commerce" schema no mysql veja mais detalhes no arquivo application.properties 
```

Rodar a aplicação
```
mvn spring-boot:run
```

Cadastrar Usuário
```
URL - > http://localhost:8080/api/cadastrar-usuario
Method -> POST
Content-Type -> application/json
Body
{
	"nome": "Luiz Evangelista",
	"email": "luiz@teste.com.br",
	"senha": "123456"
}
```

Autenticar Usuário
```
URL - > http://localhost:8080/auth
Method -> POST
Content-Type -> application/json
Body
{
	"email": "luiz@teste.com.br",
	"senha": "123456"
}
```

Cadastrar um Produto
```
URL - > http://localhost:8080/api/produtos
Method -> POST
Body
{
	"descricao": "Televisor",
	"valor": "150.00"
}
Headers
Authorization -> Bearer + token gerado no auth
Content-Type -> application/json
```
Listar todos os Produtos
```
URL - > http://localhost:8080/api/produtos
Method -> GET
Headers
Authorization -> Bearer + token gerado no auth
Content-Type -> application/json
```

Cadastrar um Pedido
```
URL - > http://localhost:8080/api/pedidos
Method -> POST
Body
{
	"descricao": "Pedido Teste",
	"enderecoEntrega": "Rua das acacias, 90",
	"usuarioId": 1,
	"produtos": [
		{
			"id": 1,
        	"descricao": "Console Playstation 4",
        	"valor": 1500
		},
		{
			"id": 2,
        	"descricao": "Televisao 4K Semp Toshiba",
        	"valor": 2550.59
		}
	]
}
Headers
Authorization -> Bearer + token gerado no auth
Content-Type -> application/json
```
Swagger
```
http://localhost:8080/swagger-ui.html
```