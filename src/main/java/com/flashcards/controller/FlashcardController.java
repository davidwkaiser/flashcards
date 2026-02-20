package com.flashcards.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flashcards.model.Flashcard;
import com.flashcards.service.FlashcardService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    @Autowired
    private FlashcardService flashcardService;

    // CREATE - Add a new flashcard
    @PostMapping
    public ResponseEntity<Flashcard> createFlashcard(@Valid @RequestBody Flashcard flashcard) {
        Flashcard savedFlashcard = flashcardService.createFlashcard(flashcard);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFlashcard);
    }

    // READ - Get all flashcards
    @GetMapping
    public ResponseEntity<List<Flashcard>> getAllFlashcards() {
        List<Flashcard> flashcards = flashcardService.getAllFlashcards();
        return ResponseEntity.ok(flashcards);
    }

    // READ - Get flashcard by ID
    @GetMapping("/{id}")
    public ResponseEntity<Flashcard> getFlashcardById(@PathVariable Long id) {
        Optional<Flashcard> flashcard = flashcardService.getFlashcardById(id);
        return flashcard.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ - Get flashcards by language
    @GetMapping("/language/{language}")
    public ResponseEntity<List<Flashcard>> getFlashcardsByLanguage(@PathVariable String language) {
        List<Flashcard> flashcards = flashcardService.getFlashcardsByLanguage(language);
        return ResponseEntity.ok(flashcards);
    }

    // READ - Get flashcards by difficulty
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<Flashcard>> getFlashcardsByDifficulty(@PathVariable Integer difficulty) {
        if (difficulty < 1 || difficulty > 3) {
            return ResponseEntity.badRequest().build();
        }
        List<Flashcard> flashcards = flashcardService.getFlashcardsByDifficulty(difficulty);
        return ResponseEntity.ok(flashcards);
    }

    // READ - Get flashcards by language and difficulty
    @GetMapping("/language/{language}/difficulty/{difficulty}")
    public ResponseEntity<List<Flashcard>> getFlashcardsByLanguageAndDifficulty(
            @PathVariable String language,
            @PathVariable Integer difficulty) {
        if (difficulty < 1 || difficulty > 3) {
            return ResponseEntity.badRequest().build();
        }
        List<Flashcard> flashcards = flashcardService.getFlashcardsByLanguageAndDifficulty(language, difficulty);
        return ResponseEntity.ok(flashcards);
    }

    // READ - Search flashcards by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Flashcard>> searchFlashcards(@RequestParam String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Flashcard> flashcards = flashcardService.searchFlashcards(keyword);
        return ResponseEntity.ok(flashcards);
    }

    // UPDATE - Update a flashcard
    @PutMapping("/{id}")
    public ResponseEntity<Flashcard> updateFlashcard(
            @PathVariable Long id,
            @Valid @RequestBody Flashcard flashcard) {
        Optional<Flashcard> updatedFlashcard = flashcardService.updateFlashcard(id, flashcard);
        return updatedFlashcard.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE - Delete a flashcard by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteFlashcard(@PathVariable Long id) {
        boolean deleted = flashcardService.deleteFlashcard(id);
        if (deleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Flashcard deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - Delete all flashcards
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteAllFlashcards() {
        flashcardService.deleteAllFlashcards();
        Map<String, String> response = new HashMap<>();
        response.put("message", "All flashcards deleted successfully");
        return ResponseEntity.ok(response);
    }

    // STATS - Get total number of flashcards
    @GetMapping("/stats/total")
    public ResponseEntity<Map<String, Long>> getTotalFlashcards() {
        long total = flashcardService.getTotalFlashcards();
        Map<String, Long> response = new HashMap<>();
        response.put("total", total);
        return ResponseEntity.ok(response);
    }

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Flashcards API");
        return ResponseEntity.ok(response);
    }
}
