# Spring Boot CRUD With In Memory DB

Building Rest API Service with Spring Data JPA Use In Memory Database

- Language Java
- Database H2

In this Project, we're going to build a Spring Boot Rest CRUD API examples with Maven that use Spring Data JPA to interact with H2 database.

Requirement
- Entity (Account)
- Entity Attribute (First Name, Last Name, Email, Data Of Birth)
- Validation (All Attribute Required) (Email Should Be Unique) (Date Of Birth Should Be Before Today)
- Create Rest API Service That Use In Memory Database
- Preform CRUD Operation

Result
- REST API Service
- Entity With Name Account And Its All Attribute Created
- Validation Implemented
- CRUD Operation Implemented
- In Memory Database (H2)

API URLs

BaseURL = "http://localhost:8082/api"

- (http://localhost:8082/api/save)
    used for save one account (Need Account Model)
- (http://localhost:8082/api/saveAll)
    used for save multiple account  (Need List Of Account Model)
- (http://localhost:8082/api/findById/{id})
    used for find account by provided Id (Need Account Id)
- (http://localhost:8082/api/deleteById/{id})
    used for delete account by provided Id (Need Account Id)
- (http://localhost:8082/api/list)
    used for getting all accounts
- (http://localhost:8082/api/search)
    used for getting account by provided Criteria  (Need Account Model)
    




