package com.flashcards.model;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class FlashcardTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void constructor_WithParameters_SetsFieldsCorrectly() {
        // Arrange & Act
        Flashcard flashcard = new Flashcard("bonjour", "hello", "French");

        // Assert
        assertEquals("bonjour", flashcard.getForeignWord());
        assertEquals("hello", flashcard.getEnglishTranslation());
        assertEquals("French", flashcard.getLanguage());
        assertEquals(1, flashcard.getDifficulty());
    }

    @Test
    void defaultConstructor_SetsDefaultDifficulty() {
        // Arrange & Act
        Flashcard flashcard = new Flashcard();

        // Assert
        assertEquals(1, flashcard.getDifficulty());
    }

    @Test
    void validation_ValidFlashcard_NoViolations() {
        // Arrange
        Flashcard flashcard = new Flashcard("hola", "hello", "Spanish");
        flashcard.setExampleSentence("Hola, ¿cómo estás?");

        // Act
        var violations = validator.validate(flashcard);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_BlankForeignWord_Violation() {
        // Arrange
        Flashcard flashcard = new Flashcard("", "hello", "Spanish");

        // Act
        var violations = validator.validate(flashcard);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Foreign word cannot be blank")));
    }

    @Test
    void validation_ForeignWordTooLong_Violation() {
        // Arrange
        String longWord = "a".repeat(101);
        Flashcard flashcard = new Flashcard(longWord, "hello", "Spanish");

        // Act
        var violations = validator.validate(flashcard);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must be between 1 and 100")));
    }

    @Test
    void validation_BlankTranslation_Violation() {
        // Arrange
        Flashcard flashcard = new Flashcard("hola", "", "Spanish");

        // Act
        var violations = validator.validate(flashcard);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Translation cannot be blank")));
    }

    @Test
    void validation_BlankLanguage_Violation() {
        // Arrange
        Flashcard flashcard = new Flashcard("hola", "hello", "");

        // Act
        var violations = validator.validate(flashcard);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Language cannot be blank")));
    }

    @Test
    void validation_ExampleSentenceTooLong_Violation() {
        // Arrange
        String longSentence = "a".repeat(501);
        Flashcard flashcard = new Flashcard("hola", "hello", "Spanish");
        flashcard.setExampleSentence(longSentence);

        // Act
        var violations = validator.validate(flashcard);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not exceed 500")));
    }

    @Test
    void onCreate_SetsTimestamps() {
        // Arrange
        Flashcard flashcard = new Flashcard("hola", "hello", "Spanish");

        // Act
        flashcard.onCreate(); // Manually call since @PrePersist won't trigger in test

        // Assert
        assertNotNull(flashcard.getCreatedAt());
        assertNotNull(flashcard.getUpdatedAt());
    }

    @Test
    void onUpdate_UpdatesTimestamp() {
        // Arrange
        Flashcard flashcard = new Flashcard("hola", "hello", "Spanish");
        LocalDateTime initialTime = LocalDateTime.now().minusMinutes(1);
        flashcard.setUpdatedAt(initialTime);

        // Act
        flashcard.onUpdate(); // Manually call since @PreUpdate won't trigger in test

        // Assert
        assertNotNull(flashcard.getUpdatedAt());
        assertTrue(flashcard.getUpdatedAt().isAfter(initialTime));
    }

    @Test
    void toString_ReturnsFormattedString() {
        // Arrange
        Flashcard flashcard = new Flashcard("hola", "hello", "Spanish");
        flashcard.setId(1L);
        flashcard.setDifficulty(2);

        // Act
        String result = flashcard.toString();

        // Assert
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("foreignWord='hola'"));
        assertTrue(result.contains("englishTranslation='hello'"));
        assertTrue(result.contains("language='Spanish'"));
        assertTrue(result.contains("difficulty=2"));
    }

    @Test
    void gettersAndSetters_WorkCorrectly() {
        // Arrange
        Flashcard flashcard = new Flashcard();
        LocalDateTime now = LocalDateTime.now();

        // Act
        flashcard.setId(1L);
        flashcard.setForeignWord("guten tag");
        flashcard.setEnglishTranslation("good day");
        flashcard.setExampleSentence("Guten Tag, wie geht es Ihnen?");
        flashcard.setLanguage("German");
        flashcard.setDifficulty(3);
        flashcard.setCreatedAt(now);
        flashcard.setUpdatedAt(now);

        // Assert
        assertEquals(1L, flashcard.getId());
        assertEquals("guten tag", flashcard.getForeignWord());
        assertEquals("good day", flashcard.getEnglishTranslation());
        assertEquals("Guten Tag, wie geht es Ihnen?", flashcard.getExampleSentence());
        assertEquals("German", flashcard.getLanguage());
        assertEquals(3, flashcard.getDifficulty());
        assertEquals(now, flashcard.getCreatedAt());
        assertEquals(now, flashcard.getUpdatedAt());
    }
}