package com.example.demo.controllers;

import com.example.demo.entities.Cours;
import com.example.demo.services.IEtudiantService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cours")
@RequiredArgsConstructor
public class CoursController {
	@Autowired
    private IEtudiantService etudiantService;

    // Endpoint to add a course (utilisé par un étudiant)
    @PostMapping(value = "/ajouter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Cours> ajouterCours(
            @RequestParam String titre,
            @RequestParam String contenu, // Changé de "description" à "contenu"
            @RequestParam(required = false) MultipartFile file,
            @RequestParam Long etudiantId) throws IOException {
        Cours cours = etudiantService.ajouterCours(titre, contenu, file);
        return new ResponseEntity<>(cours, HttpStatus.CREATED);
    }

    // Get all courses
    @GetMapping
    public ResponseEntity<List<Cours>> getAllCours() {
        List<Cours> cours = etudiantService.getAllCours();
        return new ResponseEntity<>(cours, HttpStatus.OK);
    }

    // Get a course by ID
    @GetMapping("/{id}")
    public ResponseEntity<Cours> getCoursById(@PathVariable Long id) {
        Cours cours = etudiantService.getCoursById(id);
        return ResponseEntity.ok(cours);
    }

    // Get courses by title
    @GetMapping("/titre/{titre}")
    public ResponseEntity<List<Cours>> getCoursByTitre(@PathVariable String titre) {
        List<Cours> cours = etudiantService.getCoursByTitre(titre);
        return ResponseEntity.ok(cours);
    }

    // Delete a course
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCours(@PathVariable Long id) {
        etudiantService.supprimerCours(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}