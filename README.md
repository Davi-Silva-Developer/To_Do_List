# To-Do List API

API REST para gestão de tarefas (To-Do List), desenvolvida com **Java e Spring Boot** durante o curso **Java com Spring Boot – Curso Introdutório** da Rocketseat.

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
git clone [https://github.com/Davi-Silva-Developer/nome-do-repositorio.git](https://github.com/Davi-Silva-Developer/nome-do-repositorio.git)
cd nome-do-repositorio
mvn spring-boot:run
A aplicação estará disponível em: http://localhost:8080

Endpoints principais
GET /tasks/ - Lista as tarefas associadas ao utilizador autenticado

POST /tasks/ - Cria uma nova tarefa

PUT /tasks/{id} - Atualiza as informações de uma tarefa existente

Exemplo de Requisição (POST /tasks/)
JSON
{
  "title": "Estudar Spring Boot",
  "descricao": "Rever anotações de JPA e validações",
  "hrInicio": "2024-05-10T08:00:00",
  "hrfim": "2024-05-10T12:00:00",
  "prioridade": "ALTA"
}
Conceitos aplicados
API REST

Arquitetura em camadas (Controller, Service, Repository)

Persistência com JPA / Hibernate

Integração com base de dados MySQL

Boas práticas com Spring Boot

Autor
Davi Silva LinkedIn: https://www.linkedin.com/in/davi-silva-dev

GitHub: https://github.com/Davi-Silva-Developer
