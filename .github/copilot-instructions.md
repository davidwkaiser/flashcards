# Flashcards API - Development Instructions

## Project Overview

Java Spring Boot REST API for managing foreign language word flashcards with complete CRUD operations.

## Setup Progress

- [x] Project structure created
- [x] Maven configuration (pom.xml)
- [x] Spring Boot application setup
- [x] JPA Entity (Flashcard model)
- [x] Repository layer (database access)
- [x] Service layer (business logic)
- [x] REST Controller (API endpoints)
- [x] H2 Database configuration
- [x] Documentation (README.md)

## Project Details

**Language**: Java 17
**Framework**: Spring Boot 3.2.0
**Database**: H2 (in-memory)
**Build Tool**: Maven
**API Port**: 8080

## Key Components

1. **Model**: `Flashcard.java` - Entity with JPA annotations
2. **Repository**: `FlashcardRepository.java` - Spring Data JPA
3. **Service**: `FlashcardService.java` - Business logic
4. **Controller**: `FlashcardController.java` - REST endpoints

## Building and Running

### Build
```bash
mvn clean install
```

### Run
```bash
mvn spring-boot:run
```

### Access Points
- API: http://localhost:8080/api/flashcards
- Health: http://localhost:8080/api/flashcards/health
- H2 Console: http://localhost:8080/h2-console

## API Endpoints Summary

**CRUD Operations**:
- POST /api/flashcards - Create
- GET /api/flashcards - Read all
- GET /api/flashcards/{id} - Read by ID
- PUT /api/flashcards/{id} - Update
- DELETE /api/flashcards/{id} - Delete
- DELETE /api/flashcards - Delete all

**Search & Filter**:
- GET /api/flashcards/language/{language}
- GET /api/flashcards/difficulty/{difficulty}
- GET /api/flashcards/search?keyword={keyword}

**Stats**:
- GET /api/flashcards/stats/total

## Next Steps

To extend this API:

1. Add JWT authentication
2. Implement user management
3. Add flashcard decks/categories
4. Implement spaced repetition algorithm
5. Add API documentation (Swagger/OpenAPI)
6. Set up Docker containerization
7. Add integration tests
8. Deploy to cloud (AWS, Azure, GCP)

## Notes

- H2 database is in-memory and resets on application restart
- For production, switch to PostgreSQL or MySQL
- Validation is already implemented on all fields
- CORS is enabled for cross-origin requests
