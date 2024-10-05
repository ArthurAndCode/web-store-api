# Web Store API
RESTful CRUD API designed for managing an online store. Built using Java Spring Boot and MySQL, this API provides a system to handle essential e-commerce operations such as user management, product management, order creation, and more.

## Features
* User Management: Create, login, update, delete, retrieve users and their addresses 
* Product Management: Add, update, and manage products with pricing and categories
* Order Management: Create and retrieve orders, including searching for specific types of orders (completed, paid, etc.)
* Cart Managment: Add, remove, or clear products, track the quantity of each product

![drawSQL-image](https://github.com/user-attachments/assets/6da3497b-93cb-4b67-b1e8-333087708204)

## Users

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/v1/users | Get all users | |
| GET    | /api/v1/users/{id} | Get user by id | |
| GET    | /api/v1/users/by-email/{email} | Get user by e-mail | |
| POST   | api/v1/users | Add user | [JSON](#usercreate) |
| POST   | /api/v1/users/login | Login user | [JSON](#userlogin) |
| PUT    | /api/v1/users/{id} | Edit user | [JSON](#usercreate) |
| DELETE | /api/v1/users/{id} | Delete user | |

## Products

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | ------------------------- |
| GET    | /api/v1/products | Get all products | |
| GET    | /api/v1/products{id} | Get product by id | |
| GET    | /api/v1/products/by-category/{id} | Get products by category id | |
| GET    | /api/v1/products/by-name/{name} | Get product by name | |
| POST   | /api/v1/products | Add product | [JSON](#productcreate) |
| PUT    | /api/v1/products{id} | Edit product | [JSON](#usercreate) |
| DELETE | /api/v1/products/{id} | Delete product | |

## Sample Valid JSON Request Bodys

##### <a id="usercreate">Add user -> /api/v1/users</a>
##### <a>Edit user -> /api/v1/users/{id}</a>

```json
{
    "email": "john.doe@example.com",
    "password": "securepassword",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "1234567890",
    "address": {
        "street": "Hasenheide 58",
        "city": "Berlin",
        "postalCode": "10967",
        "country": "Germany"
    }
}
```
##### <a id="userlogin">Login user -> /api/v1/users/login</a>
```json
{
    "email": "john.doe@example.com",
    "password": "securepassword"
}
```
##### <a id="productcreate">Add product -> api/v1/products</a>
##### <a>Edit product -> /api/v1/products{id}</a>

```json
{
    "name": "Cordless Power Drill",
    "description": "High-performance 18V cordless power drill with multiple speed settings and LED work light",
    "price": 89.99,
    "category": {
        "id": 1
    }
}
```

