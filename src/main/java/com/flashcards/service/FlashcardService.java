package com.flashcards.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.flashcards.model.Flashcard;
import com.flashcards.repository.FlashcardRepository;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    // Create
    public Flashcard createFlashcard(Flashcard flashcard) {
        if (flashcard == null) {
            throw new IllegalArgumentException("Flashcard cannot be null");
        }
        return flashcardRepository.save(flashcard);
    }

    // Read
    public Optional<Flashcard> getFlashcardById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return flashcardRepository.findById(id);
    }

    public List<Flashcard> getAllFlashcards() {
        // Return newest flashcards first (descending createdAt)
        return flashcardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    // public List<Flashcard> getFlashcardsByLanguage(String language) {
    //     return flashcardRepository.findByLanguageOrderByCreatedAtDesc(language);
    // }

    // public List<Flashcard> getFlashcardsByDifficulty(Integer difficulty) {
    //     return flashcardRepository.findByDifficultyOrderByCreatedAtDesc(difficulty);
    // }

    // public List<Flashcard> getFlashcardsByLanguageAndDifficulty(String language, Integer difficulty) {
    //     return flashcardRepository.findByLanguageAndDifficultyOrderByCreatedAtDesc(language, difficulty);
    // }

    // public List<Flashcard> searchFlashcards(String keyword) {
    //     return flashcardRepository.findByForeignWordContainingIgnoreCase(keyword);
    // }

    // Update
    public Optional<Flashcard> updateFlashcard(Long id, Flashcard updatedFlashcard) {
        return flashcardRepository.findById(id).map(flashcard -> {
            if (updatedFlashcard.getForeignWord() != null) {
                flashcard.setForeignWord(updatedFlashcard.getForeignWord());
            }
            if (updatedFlashcard.getEnglishTranslation() != null) {
                flashcard.setEnglishTranslation(updatedFlashcard.getEnglishTranslation());
            }
            if (updatedFlashcard.getExampleSentence() != null) {
                flashcard.setExampleSentence(updatedFlashcard.getExampleSentence());
            }
            if (updatedFlashcard.getLanguage() != null) {
                flashcard.setLanguage(updatedFlashcard.getLanguage());
            }
            if (updatedFlashcard.getDifficulty() != null) {
                flashcard.setDifficulty(updatedFlashcard.getDifficulty());
            }
            return flashcardRepository.save(flashcard);
        });
    }

    // Delete
    public boolean deleteFlashcard(Long id) {
        if (flashcardRepository.existsById(id)) {
            flashcardRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // public void deleteAllFlashcards() {
    //     flashcardRepository.deleteAll();
    // }

    public long getTotalFlashcards() {
        return flashcardRepository.count();
    }
}
