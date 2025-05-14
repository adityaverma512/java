# Product Catalog API

A RESTful API for managing a product catalog with basic CRUD operations. This repository contains a Postman collection that demonstrates the API functionality using a mock server.


## Overview

This Product Catalog API allows users to:
- Retrieve a list of all products
- Get details of a specific product
- Add new products
- Update existing products
- Delete products

The API is currently demonstrated using a Postman mock server, making it easy to explore the functionality without a full backend implementation.

## API Endpoints

| Method | Endpoint            | Description             | Request Body | Response |
|--------|--------------------|-------------------------|--------------|----------|
| GET    | `/products`         | Get all products        | None         | Array of product objects |
| GET    | `/products/{id}`    | Get product by ID       | None         | Product object |
| POST   | `/products`         | Add a new product       | Product data | Created product with ID |
| PUT    | `/products/{id}`    | Update a product        | Updated product data | Updated product object |
| PATCH  | `/products/{id}`    | Partially update a product | Partial product data | Updated product object |
| DELETE | `/products/{id}`    | Delete a product        | None         | No content (204) |


## Usage Examples

### Get All Products
- **Method**: GET
- **URL**: `https://5ab36c3e-9c58-491a-9ab8-9fdbbc4d23a0.mock.pstmn.io/products`

### Get Product by ID
- **Method**: GET
- **URL**: `https://5ab36c3e-9c58-491a-9ab8-9fdbbc4d23a0.mock.pstmn.io/products/1`

### Add New Product
- **Method**: POST
- **URL**: `https://5ab36c3e-9c58-491a-9ab8-9fdbbc4d23a0.mock.pstmn.io/products`
- **Headers**: Content-Type: application/json
- **Body**:
  ```json
  {
    "name": "Product C",
    "price": 200.0,
    "description": "Description of Product C",
    "inStock": true
  }
  ```

### Update Product
- **Method**: PUT
- **URL**: `https://5ab36c3e-9c58-491a-9ab8-9fdbbc4d23a0.mock.pstmn.io/products/1`
- **Headers**: Content-Type: application/json
- **Body**:
  ```json
  {
    "name": "Updated Product A",
    "price": 120.0,
    "description": "Updated description",
    "inStock": false
  }
  ```

### Partially Update Product
- **Method**: PATCH
- **URL**: `https://5ab36c3e-9c58-491a-9ab8-9fdbbc4d23a0.mock.pstmn.io/products/1`
- **Headers**: Content-Type: application/json
- **Body**:
  ```json
  {
    "price": 125.0,
    "inStock": true
  }
  ```

### Delete Product
- **Method**: DELETE
- **URL**: `https://5ab36c3e-9c58-491a-9ab8-9fdbbc4d23a0.mock.pstmn.io/products/1`

## Response Examples

### Get All Products Response
```json
[
  {
    "id": "1",
    "name": "Product A",
    "price": 100.0,
    "description": "Description of Product A",
    "inStock": true
  },
  {
    "id": "2",
    "name": "Product B",
    "price": 150.0,
    "description": "Description of Product B",
    "inStock": false
  }
]
```

### Get Product by ID Response
```json
{
  "id": "1",
  "name": "Product A",
  "price": 100.0,
  "description": "Description of Product A",
  "inStock": true
}
```

### Add Product Response (201 Created)
```json
{
  "id": "3",
  "name": "Product C",
  "price": 200.0,
  "description": "Description of Product C",
  "inStock": true
}
```

### Update Product Response
```json
{
  "id": "1",
  "name": "Updated Product A",
  "price": 120.0,
  "description": "Updated description",
  "inStock": false
}
```

### Partially Update Product Response
```json
{
  "id": "1",
  "name": "Product A",
  "price": 125.0,
  "description": "Description of Product A",
  "inStock": true
}
```