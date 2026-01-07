# To-Do List API

API REST para gerenciamento de tarefas (To-Do List), desenvolvida com **Java e Spring Boot** durante o curso **Java com Spring Boot – Curso Introdutório** da Rocketseat.

O projeto tem como objetivo consolidar conhecimentos em desenvolvimento backend, com foco em organização em camadas, persistência de dados e boas práticas na construção de APIs REST.

## Tecnologias
- Java 21  
- Spring Boot  
- Spring Web MVC  
- Spring Data JPA  
- Hibernate  
- MySQL  
- Lombok  
- BCrypt  
- Maven  

## Funcionalidades
- Criação de tarefas  
- Listagem de tarefas  
- Atualização de tarefas  
- Remoção de tarefas  

## Como executar o projeto

### Pré-requisitos
- Java 21  
- Maven  
- MySQL em execução  

### Passos
```bash
git clone https://github.com/Davi-Silva-Developer/nome-do-repositorio.git
cd nome-do-repositorio
mvn spring-boot:run
A aplicação estará disponível em:

arduino
Copiar código
http://localhost:8080
Endpoints principais
GET /tasks

POST /tasks

PUT /tasks/{id}

DELETE /tasks/{id}

PATCH /tasks/{id}/complete

Conceitos aplicados
API REST

Arquitetura em camadas (Controller, Service, Repository)

Persistência com JPA / Hibernate

Integração com banco de dados MySQL

Boas práticas com Spring Boot

Autor
Davi Silva
LinkedIn: https://www.linkedin.com/in/davi-silva-dev
GitHub: https://github.com/Davi-Silva-Developer
