\# Expense Tracker API



A backend REST API built with Spring Boot for managing users, expenses, and categories.



\## Features



\- User registration and login

\- JWT-based authentication

\- BCrypt password hashing

\- Role-based authorization (`USER` and `ADMIN`)

\- User CRUD operations

\- Expense CRUD operations

\- Category CRUD operations

\- Expense ownership protection

\- Input validation

\- Global exception handling

\- Proper HTTP status codes

\- MySQL database with JPA/Hibernate

\- Swagger/OpenAPI documentation



\## Tech Stack



\- Java

\- Spring Boot

\- Spring Security

\- JWT

\- Spring Data JPA

\- Hibernate

\- MySQL

\- Maven

\- Swagger / OpenAPI



\## Authentication



The application uses JWT-based authentication.



\### Register



```http

POST /auth/register

```



Example request:



```json

{

&#x20; "name": "John",

&#x20; "email": "john@gmail.com",

&#x20; "password": "password123"

}

```



\### Login



```http

POST /auth/login

```



Example request:



```json

{

&#x20; "email": "john@gmail.com",

&#x20; "password": "password123"

}

```



The login response contains a JWT token.



For protected endpoints, send the token using:



```http

Authorization: Bearer <JWT>

```



\## Authorization



The application supports two roles:



\- `USER`

\- `ADMIN`



A newly registered user is assigned the `USER` role.



Administrative user-management endpoints require the `ADMIN` role.



Users can access and manage only their own expenses.



\## API Endpoints



\### Authentication



| Method | Endpoint | Description |

|--------|----------|-------------|

| POST | `/auth/register` | Register a new user |

| POST | `/auth/login` | Login and receive JWT |



\### Users



| Method | Endpoint | Description |

|--------|----------|-------------|

| GET | `/users` | Get all users |

| GET | `/users/me` | Get currently logged-in user |

| GET | `/users/{id}` | Get user by ID |

| PUT | `/users/{id}` | Update user |

| DELETE | `/users/{id}` | Delete user |



\### Expenses



| Method | Endpoint | Description |

|--------|----------|-------------|

| GET | `/expenses` | Get current user's expenses |

| POST | `/expenses` | Create an expense |

| GET | `/expenses/{id}` | Get an expense by ID |

| PUT | `/expenses/{id}` | Update an expense |

| DELETE | `/expenses/{id}` | Delete an expense |



\### Categories



| Method | Endpoint | Description |

|--------|----------|-------------|

| GET | `/categories` | Get all categories |

| POST | `/categories` | Create a category |

| PUT | `/categories/{id}` | Update a category |

| DELETE | `/categories/{id}` | Delete a category |



\## Expense Ownership



Expenses are associated with the user who created them.



A user cannot access, update, or delete another user's expense.



\## Validation and Error Handling



The API handles common errors using a global exception handler.



| Status Code | Meaning |

|-------------|---------|

| 200 | Request successful |

| 201 | Resource created |

| 204 | Resource deleted |

| 400 | Invalid input |

| 401 | Unauthorized |

| 403 | Forbidden |

| 404 | Resource not found |

| 409 | Duplicate resource |



\## Database



The application uses MySQL with Spring Data JPA and Hibernate.



Create the database using:



```sql

CREATE DATABASE expense\_tracker;

```



Database credentials are provided through environment variables.



\## Environment Variables



The application expects:



```text

DB\_USERNAME

DB\_PASSWORD

JWT\_SECRET

JWT\_EXPIRATION

```



Example:



```text

DB\_USERNAME=root

DB\_PASSWORD=your\_password

JWT\_SECRET=your\_secret

JWT\_EXPIRATION=86400000

```



Do not commit real passwords or JWT secrets to the repository.



\## Swagger / OpenAPI



Swagger UI is available at:



```text

http://localhost:8080/swagger-ui/index.html

```



Swagger can be used to:



\- Explore API endpoints

\- View request and response information

\- Test API endpoints

\- Authenticate using JWT



Use the \*\*Authorize\*\* button in Swagger to provide the JWT token.



\## Project Structure



```text

src/main/java/com/projects/expense\_tracker\_api

│

├── config

├── controller

├── dto

├── entity

├── exception

├── mapper

├── repository

├── security

└── service

```



\## Running the Project



\### 1. Clone the repository



```bash

git clone <repository-url>

```



\### 2. Create the MySQL database



```sql

CREATE DATABASE expense\_tracker;

```



\### 3. Configure environment variables



Set:



```text

DB\_USERNAME

DB\_PASSWORD

JWT\_SECRET

JWT\_EXPIRATION

```



\### 4. Run the application



Using Maven:



```bash

./mvnw spring-boot:run

```



Or run the Spring Boot application from Eclipse.



\### 5. Open Swagger



```text

http://localhost:8080/swagger-ui/index.html

```



\## Author



Shivam Kumar



