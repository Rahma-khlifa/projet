package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import com.example.demo.entities.Cours;
import com.example.demo.entities.Etudiant;
import com.example.demo.services.CoursService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cours")
@RequiredArgsConstructor
public class CoursController {
    @Autowired
    private  CoursService coursService;

    // Endpoint to add a course
    @PostMapping(value = "/ajouter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Cours> ajouterCours(
            @RequestParam("titre") String titre,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("etudiantId") Long etudiantId) throws IOException {
        
        Cours cours = coursService.ajouterCours(titre, description, file, etudiantId);
        return ResponseEntity.ok(cours);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cours> getCoursById(@PathVariable("id") Long id) {
        Cours cours = coursService.getCoursById(id);
        if (cours != null) {
            return ResponseEntity.ok(cours);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/titre/{titre}")
    public ResponseEntity<List<Cours>> getCoursByTitre(@PathVariable("titre") String titre) {
        List<Cours> cours = coursService.getCoursByTitre(titre);
        if (!cours.isEmpty()) {
            return ResponseEntity.ok(cours);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
