package com.flashcards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flashcards.model.Flashcard;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findByLanguage(String language);
    List<Flashcard> findByDifficulty(Integer difficulty);
    List<Flashcard> findByLanguageAndDifficulty(String language, Integer difficulty);
    List<Flashcard> findByForeignWordContainingIgnoreCase(String keyword);
}
