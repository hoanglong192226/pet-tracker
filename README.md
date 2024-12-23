# Pet Tracker
### Description
Pet Tracker is a robust application designed to used in **Pet Clinics** to manage pet-related data efficiently.
It provides separate functionalities for authentication, administrative tasks, and management of pets and their owners
### ServerSide
Server is a RESTful webservice that provides APIs to support UI. It has several implemented controllers:

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

### UI
A mobile-first responsive UI for users to interact with the server-side. Supports several functionalities:

1. **Login/Logout**
   - Handles user authentication.
   - Supports login and logout functionalities.
2. **Owners**
   - Allow users to interact with Owner (Add/Edit/Delete)
3. **Pet**
   - Allow users to interact with Pets (Add/Edit/Delete)
   - Allow users to assign an Owner to a Pet
4. **Users (Admin only)**
   - Allow an Admin user to Add/Edit/Delete a user
   - Can only be accessed by an Admin

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

#### UI
- **React.js 19**: Library to develop the application.
- **Next.js 15 (App Router)**: Framework for application development.
- **Zod**: Schemas declaration and validation.
- **TailwindCSS**: CSS framework to build responsive and mobile first application
- **Eslint**: Code formatting and rule applying for application development.

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

#### Super Admin
Unable to delete/modify super admin
````
username: admin
password: pHrJvh4ICt2Fh7Qu
````

---

#### Access UI
````
http://localhost:3000
````

---

#### Database configuration
````
db: pet_tracker
username: postgres
password: pHrJvh4ICt2Fh7Qu
````

---

#### Sample for API cURL
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

### Screenshots from UI
/login
![img.png](readme-images/img.png)
![img.png](readme-images/img_7.png)
Mobile UI
![img_1.png](readme-images/img_8.png)
/owners/list
![img_1.png](readme-images/img_1.png)
/owners?id=XX
![img_3.png](readme-images/img_3.png)
/pets/list
![img_2.png](readme-images/img_2.png)
/pets?id=XX
![img_4.png](readme-images/img_4.png)
/admin/users/list
![img_5.png](readme-images/img_5.png)
/admin/users?id=XX
![img_6.png](readme-images/img_6.png)

