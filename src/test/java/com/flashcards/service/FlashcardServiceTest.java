package com.flashcards.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.flashcards.model.Flashcard;
import com.flashcards.repository.FlashcardRepository;

@ExtendWith(MockitoExtension.class)
class FlashcardServiceTest {

    @Mock
    private FlashcardRepository flashcardRepository;

    @InjectMocks
    private FlashcardService flashcardService;

    private Flashcard testFlashcard;

    @BeforeEach
    void setUp() {
        testFlashcard = new Flashcard("hola", "hello", "Spanish");
        testFlashcard.setId(1L);
        testFlashcard.setDifficulty(1);
        testFlashcard.setExampleSentence("Hola, ¿cómo estás?");
        testFlashcard.setCreatedAt(LocalDateTime.now());
        testFlashcard.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void createFlashcard_ValidFlashcard_ReturnsSavedFlashcard() {
        // Arrange
        when(flashcardRepository.save(any(Flashcard.class))).thenReturn(testFlashcard);

        // Act
        Flashcard result = flashcardService.createFlashcard(testFlashcard);

        // Assert
        assertNotNull(result);
        assertEquals(testFlashcard.getForeignWord(), result.getForeignWord());
        verify(flashcardRepository, times(1)).save(testFlashcard);
    }

    @Test
    void createFlashcard_NullFlashcard_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> flashcardService.createFlashcard(null));
        assertEquals("Flashcard cannot be null", exception.getMessage());
    }

    @Test
    void getFlashcardById_ValidId_ReturnsFlashcard() {
        // Arrange
        when(flashcardRepository.findById(1L)).thenReturn(Optional.of(testFlashcard));

        // Act
        Optional<Flashcard> result = flashcardService.getFlashcardById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testFlashcard.getId(), result.get().getId());
        verify(flashcardRepository, times(1)).findById(1L);
    }

    @Test
    void getFlashcardById_NullId_ThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> flashcardService.getFlashcardById(null));
        assertEquals("ID cannot be null", exception.getMessage());
    }

    @Test
    void getAllFlashcards_ReturnsSortedList() {
        // Arrange
        List<Flashcard> flashcards = Arrays.asList(testFlashcard);
        when(flashcardRepository.findAll(any(Sort.class))).thenReturn(flashcards);

        // Act
        List<Flashcard> result = flashcardService.getAllFlashcards();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(flashcardRepository, times(1)).findAll(any(Sort.class));
    }

    @Test
    void updateFlashcard_ExistingId_ReturnsUpdatedFlashcard() {
        // Arrange
        Flashcard updatedFlashcard = new Flashcard("adiós", "goodbye", "Spanish");
        when(flashcardRepository.findById(1L)).thenReturn(Optional.of(testFlashcard));
        when(flashcardRepository.save(any(Flashcard.class))).thenReturn(testFlashcard);

        // Act
        Optional<Flashcard> result = flashcardService.updateFlashcard(1L, updatedFlashcard);

        // Assert
        assertTrue(result.isPresent());
        verify(flashcardRepository, times(1)).findById(1L);
        verify(flashcardRepository, times(1)).save(testFlashcard);
    }

    @Test
    void updateFlashcard_NonExistingId_ReturnsEmpty() {
        // Arrange
        when(flashcardRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Flashcard> result = flashcardService.updateFlashcard(1L, testFlashcard);

        // Assert
        assertFalse(result.isPresent());
        verify(flashcardRepository, times(1)).findById(1L);
        verify(flashcardRepository, never()).save(any(Flashcard.class));
    }

    @Test
    void deleteFlashcard_ExistingId_ReturnsTrue() {
        // Arrange
        when(flashcardRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean result = flashcardService.deleteFlashcard(1L);

        // Assert
        assertTrue(result);
        verify(flashcardRepository, times(1)).existsById(1L);
        verify(flashcardRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteFlashcard_NonExistingId_ReturnsFalse() {
        // Arrange
        when(flashcardRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean result = flashcardService.deleteFlashcard(1L);

        // Assert
        assertFalse(result);
        verify(flashcardRepository, times(1)).existsById(1L);
        verify(flashcardRepository, never()).deleteById(1L);
    }

    @Test
    void getTotalFlashcards_ReturnsCount() {
        // Arrange
        when(flashcardRepository.count()).thenReturn(5L);

        // Act
        long result = flashcardService.getTotalFlashcards();

        // Assert
        assertEquals(5L, result);
        verify(flashcardRepository, times(1)).count();
    }
}