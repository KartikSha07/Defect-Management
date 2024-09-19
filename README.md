# Jira - Defect Management

Jira - Defect Management is a Spring Boot application designed to manage and track defects (bugs) in software projects. This application provides a RESTful API for creating, updating, and retrieving defect information, as well as generating reports.

## Technologies Used

- **Spring Boot 3.2.2**
  - **Data JPA**
  - **Web**
  - **AOP**
  - **Devtools**
- **H2 Database** (for testing)
- **springdoc-openapi** 2.4.0
- **JUnit 5**
  - **junit-jupiter-api**
  - **junit-jupiter-engine**
- **Mockito** 3.12.4
- **Lombok**
- **SLF4J** 2.0.11
- **Logback** 1.4.14
- **Validation API** 2.0.1.Final
- **Hibernate Validator** 7.0.2.Final
- **MySQL Connector Java** 8.0.33
- **Jacoco** 0.8.7

## Getting Started

### Prerequisites

- Java 17
- Maven
- MySQL

### Setup

1. **Clone the repository:**
    ```bash
    git clone https://github.com/yourusername/defects-management.git
    cd defects-management
    ```

2. **Configure the database:**

   Update the `application.properties` file with your MySQL database configuration.

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/yourdatabase
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    ```

3. **Build and run the application:**

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4. **Access the API documentation:**

   Open your browser and go to `http://localhost:8080/swagger-ui.html` to view and interact with the API documentation.

## API Endpoints

### Authentication

- **Authenticate User**
  - **URL:** `/api/users/login`
  - **Method:** `POST`
  - **Description:** Authenticate valid user credentials.
  - **Request Body:**
    ```json
    {
      "userName": "string",
      "password": "string"
    }
    ```
  - **Response:**
    - **200 OK**
      ```json
      {
        "userName": "string",
        "password": "string",
        "role": "string",
        "accountLocked": false
      }
      ```
    - **401 Unauthorized**
      ```json
      {
        "error": "Invalid username or password"
      }
      ```

### Defect Management

- **Create a new defect**
  - **URL:** `/api/defects/new`
  - **Method:** `POST`
  - **Description:** Create a new defect.
  - **Request Body:**
    ```json
    {
      "title": "string",
      "defectdetails": "string",
      "stepstoreproduce": "string",
      "priority": "string",
      "detectedon": "YYYY-MM-DD",
      "expectedresolution": "YYYY-MM-DD",
      "reportedbytesterid": "string",
      "assignedtodeveloperid": "string",
      "severity": "string",
      "status": "string",
      "projectcode": "string",
      "resolutions": [
        {
          "resolutiondate": "YYYY-MM-DD",
          "resolution": "string"
        }
      ]
    }
    ```
  - **Response:**
    - **201 Created**
      ```json
      {
        "message": "Success"
      }
      ```
    - **403 Forbidden**
      ```json
      {
        "error": "Maximum bug assignment limit reached for today."
      }
      ```
    - **400 Bad Request**
      ```json
      {
        "error": "Error message"
      }
      ```

- **Update the status and provide resolution to a defect**
  - **URL:** `/api/defects/resolve`
  - **Method:** `PUT`
  - **Description:** Update the status and provide resolution to a defect.
  - **Request Body:**
    ```json
    {
      "id": 1,
      "status": "string",
      "resolutions": [
        {
          "resolutiondate": "YYYY-MM-DD",
          "resolution": "string"
        }
      ]
    }
    ```
  - **Response:**
    - **200 OK**
      ```json
      {
        "message": "Success"
      }
      ```
    - **404 Not Found**
      ```json
      {
        "error": "Defect not found"
      }
      ```

- **Get defects assigned to a developer**
  - **URL:** `/api/defects/assignedto/{developerId}`
  - **Method:** `GET`
  - **Description:** Get defects based on DeveloperId.
  - **Response:**
    - **200 OK**
      ```json
      [
        {
          "id": 1,
          "title": "string",
          "defectdetails": "string",
          "stepstoreproduce": "string",
          "priority": "string",
          "detectedon": "YYYY-MM-DD",
          "expectedresolution": "YYYY-MM-DD",
          "reportedbytesterid": "string",
          "assignedtodeveloperid": "string",
          "severity": "string",
          "status": "string",
          "projectcode": "string",
          "resolutions": [
            {
              "id": 1,
              "defectId": 1,
              "resolutiondate": "YYYY-MM-DD",
              "resolution": "string"
            }
          ]
        }
      ]
      ```

- **Get defect details by ID**
  - **URL:** `/api/defects/{defectId}`
  - **Method:** `GET`
  - **Description:** Get defects based on Id.
  - **Response:**
    - **200 OK**
      ```json
      {
        "id": 1,
        "title": "string",
        "defectdetails": "string",
        "stepstoreproduce": "string",
        "priority": "string",
        "detectedon": "YYYY-MM-DD",
        "expectedresolution": "YYYY-MM-DD",
        "reportedbytesterid": "string",
        "assignedtodeveloperid": "string",
        "severity": "string",
        "status": "string",
        "projectcode": "string",
        "resolutions": [
          {
            "id": 1,
            "defectId": 1,
            "resolutiondate": "YYYY-MM-DD",
            "resolution": "string"
          }
        ]
      }
      ```

- **Generate report based on project code**
  - **URL:** `/api/defects/report/{projectId}`
  - **Method:** `GET`
  - **Description:** Generate report based on project code.
  - **Response:**
    - **200 OK**
      ```json
      {
        "projectId": 1,
        "defects": [
          {
            "id": 1,
            "title": "string",
            "defectdetails": "string",
            "stepstoreproduce": "string",
            "priority": "string",
            "detectedon": "YYYY-MM-DD",
            "expectedresolution": "YYYY-MM-DD",
            "reportedbytesterid": "string",
            "assignedtodeveloperid": "string",
            "severity": "string",
            "status": "string",
            "projectcode": "string",
            "resolutions": [
              {
                "id": 1,
                "defectId": 1,
                "resolutiondate": "YYYY-MM-DD",
                "resolution": "string"
              }
            ]
          }
        ]
      }
      ```

- **Get all defects**
  - **URL:** `/api/defects/getAll`
  - **Method:** `GET`
  - **Description:** Get all defects.
  - **Response:**
    - **200 OK**
      ```json
      [
        {
          "id": 1,
          "title": "string",
          "defectdetails": "string",
          "stepstoreproduce": "string",
          "priority": "string",
          "detectedon": "YYYY-MM-DD",
          "expectedresolution": "YYYY-MM-DD",
          "reportedbytesterid": "string",
          "assignedtodeveloperid": "string",
          "severity": "string",
          "status": "string",
          "projectcode": "string",
          "resolutions": [
            {
              "id": 1,
              "defectId": 1,
              "resolutiondate": "YYYY-MM-DD",
              "resolution": "string"
            }
          ]
        }
      ]
      ```


## Acknowledgments

- Thanks to the Spring Boot community for their comprehensive documentation and support.
- Inspired by Jira for defect management concepts.
