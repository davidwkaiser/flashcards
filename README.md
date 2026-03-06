# Flashcards API

A Spring Boot REST API for managing foreign language word flashcards with CRUD operations and advanced search capabilities.

## Features

- **CRUD Operations**: Create, Read, Update, and Delete flashcards
- **Search Functionality**: Search flashcards by keyword
- **Filter Options**: Filter by language and difficulty level
- **Validation**: Input validation for all flashcard fields
- **In-Memory Database**: H2 database for quick setup and testing
- **RESTful API**: Complete REST endpoints for all operations
- **Error Handling**: Comprehensive error responses

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: H2 (in-memory)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Server**: Embedded Tomcat

## Project Structure

```
flashcards/
├── src/
│   ├── main/
│   │   ├── java/com/flashcards/
│   │   │   ├── controller/     # REST endpoints
│   │   │   ├── service/        # Business logic
│   │   │   ├── repository/     # Data access layer
│   │   │   ├── model/          # JPA entities
│   │   │   └── FlashcardsApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/java/com/flashcards/
├── pom.xml
└── README.md
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Installation & Setup

1. Clone the repository (or navigate to the project directory)
2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The API will start on `http://localhost:8080`

## API Endpoints

### Health Check
- `GET /api/flashcards/health` - Check API status

### Create
- `POST /api/flashcards` - Create a new flashcard

**Request Body**:
```json
{
  "foreignWord": "bonjour",
  "translation": "hello",
  "language": "French",
  "definition": "A greeting used in the morning",
  "difficulty": 1
}
```

### Read
- `GET /api/flashcards` - Get all flashcards
- `GET /api/flashcards/{id}` - Get flashcard by ID
- `GET /api/flashcards/language/{language}` - Get flashcards by language
- `GET /api/flashcards/difficulty/{difficulty}` - Get flashcards by difficulty (1-3)
- `GET /api/flashcards/language/{language}/difficulty/{difficulty}` - Get flashcards by language and difficulty
- `GET /api/flashcards/search?keyword={keyword}` - Search flashcards by keyword
- `GET /api/flashcards/stats/total` - Get total count of flashcards

### Update
- `PUT /api/flashcards/{id}` - Update a flashcard

**Request Body**:
```json
{
  "foreignWord": "bonjour",
  "translation": "hello",
  "definition": "A common morning greeting",
  "difficulty": 2
}
```

### Delete
- `DELETE /api/flashcards/{id}` - Delete a flashcard by ID
- `DELETE /api/flashcards` - Delete all flashcards

## Example Usage

### Create a Flashcard
```bash
curl -X POST http://localhost:8080/api/flashcards \
  -H "Content-Type: application/json" \
  -d '{
    "foreignWord": "hola",
    "translation": "hello",
    "language": "Spanish",
    "difficulty": 1
  }'
```

### Get All Flashcards
```bash
curl http://localhost:8080/api/flashcards
```

### Get Flashcards by Language
```bash
curl http://localhost:8080/api/flashcards/language/Spanish
```

### Search Flashcards
```bash
curl "http://localhost:8080/api/flashcards/search?keyword=hello"
```

### Update a Flashcard
```bash
curl -X PUT http://localhost:8080/api/flashcards/1 \
  -H "Content-Type: application/json" \
  -d '{
    "translation": "hi",
    "difficulty": 2
  }'
```

### Delete a Flashcard
```bash
curl -X DELETE http://localhost:8080/api/flashcards/1
```

## Database

The application uses an H2 in-memory database. You can access the H2 console at:
- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (leave blank)

## Flashcard Model

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| id | Long | Auto | Unique identifier |
| foreignWord | String | Yes | The word in foreign language (1-100 chars) |
| translation | String | Yes | English translation (1-100 chars) |
| definition | String | No | Additional definition (max 500 chars) |
| language | String | Yes | Language name (e.g., Spanish, French, German) |
| difficulty | Integer | No | Difficulty level (1=Easy, 2=Medium, 3=Hard) |
| createdAt | LocalDateTime | Auto | Creation timestamp |
| updatedAt | LocalDateTime | Auto | Last update timestamp |

## Testing

Run the tests with:
```bash
mvn test
```

## Development

### Adding New Features

1. **New API Endpoint**: Add controller method in `FlashcardController.java`
2. **New Business Logic**: Add service method in `FlashcardService.java`
3. **New Query**: Add repository method in `FlashcardRepository.java`

### Database Schema

The application automatically creates the schema on startup. The `Flashcard` table includes:
- Primary key on `id`
- Indexed columns for `language` and `difficulty` for faster queries
- Timestamps for tracking creation and updates

## Configuration

Configuration can be modified in `src/main/resources/application.properties`:

```properties
server.port=8080                                    # Change API port
spring.jpa.show-sql=true                          # Enable SQL logging
spring.jpa.properties.hibernate.format_sql=true   # Format SQL output
```

## Authentication

- `POST /api/auth/register` - Register a new user. Request body JSON: `{ "email": "user@example.com", "password": "secret" }`
- `POST /api/auth/login` - Login with email and password. Returns JSON `{ "token": "<jwt>", "email": "user@example.com" }`

Notes:
- Include the token in subsequent requests using the header `Authorization: Bearer <token>` to access protected endpoints.
- The backend issues JWTs; store tokens securely on the frontend (e.g., in memory or secure storage) and attach to requests from the React app.

## Future Enhancements

- [ ] User authentication and authorization
- [ ] Categories/Decks for organizing flashcards
- [ ] Spaced repetition algorithm
- [ ] User progress tracking
- [ ] Export/Import flashcards
- [ ] Integration with external vocabulary APIs
- [ ] Caching layer
- [ ] API documentation with Swagger/OpenAPI

## License

This project is open source and available under the MIT License.

## Support

For issues or questions, please create an issue in the repository.
