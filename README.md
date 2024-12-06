# Pet Tracker
### Description
Pet Tracker is a robust application designed to manage pet-related data efficiently.
It provides separate functionalities for authentication, administrative tasks, and management of pets and their owners
### ServerSide
Server is a RESTful webservice that provides APIs to support UI(*Inprogress*). It has several implemented controllers:

1. **AuthController**
   - Handles user authentication.
   - Supports login and logout functionalities.
   - Implements JWT-based authentication with HttpOnly and SameSite cookies for enhanced security.
2. **AdminController**
   - Manages administrative tasks such as user management
   - Supports add/edit/delete users
3. **PetController**
    - Facilitates CRUD operations for pet data.
    - Supports add/edit/delete pets
4. **OwnerController**
    - Manages owner-related information, including CRUD operations for owner profiles.
    - Supports add/edit/delete owners

### UI (*inprogress*)

---

### Libraries and Technologies Used
#### Serverside
- **Java 11**: Programming language.
- **Spring Boot**: Framework for application development
- **Spring Security**: For authentication and authorization
- **PostgreSQL**: Relational database management system.
- **Maven**: Dependency management and build tool.
- **ELK** (Elasticsearch, Logstash, Kibana): For centralized logging and monitoring.
- **Docker** Compose: Container orchestration tool.
- **Flyway**: Database version control and migrations.

#### UI (*inprogress*)

---

### How to Run the Application
#### Build the Application
Run the following command to clean and build the application:
````
mvn clean install
````

#### Start the Application
````
docker-compose -f docker-compose.yml up -d
````

---

#### cURL for API
````
curl --location 'http://localhost:8080/api/v1/pets' \
--header 'Content-Type: application/json' \
--header 'Cookie: token=eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9tZW1iZXIiLCJzdWIiOiJhZG1pbiIsImlhdCI6MTczMzIxOTYyNywiZXhwIjoxNzMzMjIzMjI3fQ.MS8UTdgGGsiQUVYvix6qhzWY2Z8VXCm3by4ofj4XqcA' \
--data '
  {
    "pets": [
        {
            "id": 5,
            "name": "bap",
            "age": 8,
            "weight": 7.8,
            "type": "DOG",
            "medicalNote": "vaccinated",
            "ownerId": 6
        }
    ]
   
  }
'
````

---

#### Swagger UI (dev environment only)
````
http://localhost:8080/swagger-ui/index.html
````

---

#### Log monitoring
````
http://localhost:5601
````
Note: Need to create data-view and dashboard before using

---

### Additional Notes
- Ensure Docker and Docker Compose are installed on your system.
- Verify that the required environment variables for the database and application configurations are set before running the application.
- Use the provided docker-compose.yml to configure services like PostgreSQL and ELK for seamless integration.
- Supports 2 roles: "**admin**" and "**user**".
- Supports pet type: "**dog**", "**cat**" and "**unknown**". Can add more pet type by using admin API (*future enhancement*)