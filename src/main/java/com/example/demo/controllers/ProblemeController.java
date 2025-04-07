package com.example.demo.controllers;

import com.example.demo.entities.Probleme;
import com.example.demo.entities.Reponse;
import com.example.demo.services.IEtudiantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/problemes")
public class ProblemeController {
    
    @Autowired
    private IEtudiantService etudiantService;
    
    // Get all problems
    @GetMapping
    public ResponseEntity<List<Probleme>> getAllProblemes() {
        return ResponseEntity.ok(etudiantService.getAllProblemes());
    }
    
    // Get a specific problem by ID
    @GetMapping("/{id}")
    public ResponseEntity<Probleme> getProblemeById(@PathVariable int id) {
        Probleme probleme = etudiantService.getProblemeById(id);
        if (probleme != null) {
            return ResponseEntity.ok(probleme);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Get problems by student ID
    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Probleme>> getProblemesByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(etudiantService.getProblemesByEtudiant(etudiantId));
    }
    
    // Create a new problem
    @PostMapping
    public ResponseEntity<Probleme> createProbleme(
            @RequestBody Map<String, Object> payload) {
        
        Long etudiantId = Long.parseLong(payload.get("etudiantId").toString());
        String titre = payload.get("titre").toString();
        String description = payload.get("description").toString();
        
        Probleme probleme = etudiantService.publierProbleme(etudiantId, titre, description);
        return new ResponseEntity<>(probleme, HttpStatus.CREATED);
    }
    
    // Get responses for a problem
    @GetMapping("/{problemeId}/reponses")
    public ResponseEntity<List<Reponse>> getReponsesByProbleme(@PathVariable int problemeId) {
        return ResponseEntity.ok(etudiantService.getReponsesByProbleme(problemeId));
    }
    
    // Add a response to a problem
    @PostMapping("/{problemeId}/reponses")
    public ResponseEntity<Reponse> addReponseToProbleme(
            @PathVariable int problemeId,
            @RequestBody Map<String, Object> payload) {
        
        Long etudiantId = Long.parseLong(payload.get("etudiantId").toString());
        String contenu = payload.get("contenu").toString();
        
        Reponse reponse = etudiantService.publierReponse(etudiantId, problemeId, contenu);
        return new ResponseEntity<>(reponse, HttpStatus.CREATED);
    }
}