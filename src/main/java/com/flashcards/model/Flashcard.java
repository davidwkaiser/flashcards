package com.flashcards.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "flashcards")
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Foreign word cannot be blank")
    @Size(min = 1, max = 100, message = "Foreign word must be between 1 and 100 characters")
    @Column(nullable = false)
    private String foreignWord;

    @NotBlank(message = "Translation cannot be blank")
    @Size(min = 1, max = 100, message = "Translation must be between 1 and 100 characters")
    @Column(nullable = false)
    private String englishTranslation;

    @Size(max = 500, message = "Example sentence must not exceed 500 characters")
    private String exampleSentence;

    @NotBlank(message = "Language cannot be blank")
    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private Integer difficulty = 1; // 1 (Easy), 2 (Medium), 3 (Hard)

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Flashcard() {
    }

    public Flashcard(String foreignWord, String englishTranslation, String language) {
        this.foreignWord = foreignWord;
        this.englishTranslation = englishTranslation;
        this.language = language;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getForeignWord() {
        return foreignWord;
    }

    public void setForeignWord(String foreignWord) {
        this.foreignWord = foreignWord;
    }

    public String getEnglishTranslation() {
        return englishTranslation;
    }

    public void setEnglishTranslation(String englishTranslation) {
        this.englishTranslation = englishTranslation;
    }

    public String getExampleSentence() {
        return exampleSentence;
    }

    public void setExampleSentence(String exampleSentence) {
        this.exampleSentence = exampleSentence;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "id=" + id +
                ", foreignWord='" + foreignWord + '\'' +
                ", englishTranslation='" + englishTranslation + '\'' +
                ", language='" + language + '\'' +
                ", difficulty=" + difficulty +
                '}';
    }
}
