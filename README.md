# Ifood REST API
SpringBoot CP1 - JAVA

## Descrição
API REST para sistema de delivery de comida similar ao iFood, desenvolvida em Java com Spring Boot para checkpoint acadêmico.

## Tecnologias Utilizadas
- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database (em memória)
- Maven
- Bean Validation

## Como Executar
1. Clone o repositório
2. Navegue até o diretório do projeto
3. Execute: `mvn spring-boot:run`
4. A API estará disponível em: `http://localhost:8080`

## Endpoints da API

### Restaurantes (`/api/restaurants`)

#### GET /api/restaurants
Lista todos os restaurantes
```bash
curl -X GET http://localhost:8080/api/restaurants
```

#### GET /api/restaurants/{id}
Busca restaurante por ID
```bash
curl -X GET http://localhost:8080/api/restaurants/1
```

#### POST /api/restaurants
Cria novo restaurante
```bash
curl -X POST http://localhost:8080/api/restaurants \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Pizza Palace",
    "address": "123 Main Street, Downtown",
    "phone": "+1-555-0123",
    "deliveryFee": 5.50
  }'
```

#### PUT /api/restaurants/{id}
Atualiza restaurante
```bash
curl -X PUT http://localhost:8080/api/restaurants/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Pizza Palace Updated",
    "address": "123 Main Street, Downtown",
    "phone": "+1-555-0123",
    "deliveryFee": 6.00
  }'
```

#### DELETE /api/restaurants/{id}
Remove restaurante
```bash
curl -X DELETE http://localhost:8080/api/restaurants/1
```

#### GET /api/restaurants/search
Busca restaurantes por nome ou endereço
```bash
curl -X GET "http://localhost:8080/api/restaurants/search?name=Pizza"
curl -X GET "http://localhost:8080/api/restaurants/search?address=Downtown"
```

### Itens do Cardápio (`/api/menu-items`)

#### GET /api/menu-items
Lista todos os itens do cardápio
```bash
curl -X GET http://localhost:8080/api/menu-items
```

#### GET /api/menu-items/{id}
Busca item do cardápio por ID
```bash
curl -X GET http://localhost:8080/api/menu-items/1
```

#### POST /api/menu-items
Cria novo item do cardápio
```bash
curl -X POST http://localhost:8080/api/menu-items \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Margherita Pizza",
    "description": "Fresh tomato sauce, mozzarella, and basil",
    "price": 15.99,
    "available": true,
    "restaurant": {"id": 1}
  }'
```

#### PUT /api/menu-items/{id}
Atualiza item do cardápio
```bash
curl -X PUT http://localhost:8080/api/menu-items/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Margherita Pizza Deluxe",
    "description": "Fresh tomato sauce, premium mozzarella, and fresh basil",
    "price": 18.99,
    "available": true,
    "restaurant": {"id": 1}
  }'
```

#### DELETE /api/menu-items/{id}
Remove item do cardápio
```bash
curl -X DELETE http://localhost:8080/api/menu-items/1
```

#### GET /api/menu-items/restaurant/{restaurantId}
Lista itens do cardápio por restaurante
```bash
curl -X GET http://localhost:8080/api/menu-items/restaurant/1
curl -X GET "http://localhost:8080/api/menu-items/restaurant/1?availableOnly=true"
```

#### GET /api/menu-items/search
Busca itens por nome
```bash
curl -X GET "http://localhost:8080/api/menu-items/search?name=Pizza"
```

### Pedidos (`/api/orders`)

#### GET /api/orders
Lista todos os pedidos
```bash
curl -X GET http://localhost:8080/api/orders
```

#### GET /api/orders/{id}
Busca pedido por ID
```bash
curl -X GET http://localhost:8080/api/orders/1
```

#### POST /api/orders
Cria novo pedido
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "João Silva",
    "customerAddress": "Rua das Flores, 456, São Paulo",
    "customerPhone": "+55-11-99999-9999",
    "restaurant": {"id": 1},
    "orderItems": []
  }'
```

#### PUT /api/orders/{id}
Atualiza pedido
```bash
curl -X PUT http://localhost:8080/api/orders/1 \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "João Silva",
    "customerAddress": "Rua das Flores, 456, São Paulo",
    "customerPhone": "+55-11-99999-9999",
    "restaurant": {"id": 1},
    "orderItems": []
  }'
```

#### DELETE /api/orders/{id}
Remove pedido
```bash
curl -X DELETE http://localhost:8080/api/orders/1
```

#### PATCH /api/orders/{id}/status
Atualiza status do pedido
```bash
curl -X PATCH "http://localhost:8080/api/orders/1/status?status=CONFIRMED"
```

#### GET /api/orders/restaurant/{restaurantId}
Lista pedidos por restaurante
```bash
curl -X GET http://localhost:8080/api/orders/restaurant/1
```

#### GET /api/orders/status/{status}
Lista pedidos por status
```bash
curl -X GET http://localhost:8080/api/orders/status/PENDING
```

#### GET /api/orders/search
Busca pedidos por nome do cliente
```bash
curl -X GET "http://localhost:8080/api/orders/search?customerName=João"
```

## Status de Pedidos
- `PENDING` - Pendente
- `CONFIRMED` - Confirmado
- `PREPARING` - Preparando
- `OUT_FOR_DELIVERY` - Saiu para entrega
- `DELIVERED` - Entregue
- `CANCELLED` - Cancelado

## Estrutura do Banco de Dados

### Entidades
- **Restaurant**: Restaurantes com nome, endereço, telefone e taxa de entrega
- **MenuItem**: Itens do cardápio com nome, descrição, preço e disponibilidade
- **Order**: Pedidos com dados do cliente e status
- **OrderItem**: Itens do pedido com quantidade e preço unitário

### Relacionamentos
- Restaurant 1:N MenuItem
- Restaurant 1:N Order  
- Order 1:N OrderItem
- MenuItem 1:N OrderItem

## Console H2
Para visualizar o banco de dados em desenvolvimento, acesse:
`http://localhost:8080/h2-console`

**Configurações de conexão:**
- JDBC URL: `jdbc:h2:mem:ifooddb`
- Username: `sa`
- Password: (vazio)

## Validações
- Campos obrigatórios são validados
- Preços devem ser positivos
- Telefones e endereços são obrigatórios
- Total do pedido é calculado automaticamente

## Tratamento de Erros
A API retorna erros estruturados com:
- Status code HTTP apropriado
- Timestamp
- Mensagem de erro
- Detalhes de validação (quando aplicável)
