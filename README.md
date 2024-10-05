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
| GET    | /api/v1/users/{id} | Get user by his id | |
| GET    | /api/v1/users/by-email/{email} | Get user by his e-mail | |
| POST   | api/v1/users | Add user | [JSON](#usercreate) |
| POST   | /api/v1/users/login | Login user | [JSON](#userlogin) |
| PUT    | /api/v1/users/{id} | Edit user | [JSON](#userupdate) |
| DELETE | /api/v1/users/{id} | Delete user | |

## Sample Valid JSON Request Bodys

##### <a id="usercreate">Add user -> /api/v1/users</a>
#####  <a id="userupdate">Edit user -> /api/v1/users</a>
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

