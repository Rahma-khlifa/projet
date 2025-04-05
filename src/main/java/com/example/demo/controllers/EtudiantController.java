package com.example.demo.controllers;

import com.example.demo.entities.Etudiant;
import com.example.demo.services.IEtudiantService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantController {
	@Autowired
    private IEtudiantService etudiantService;

    @PostMapping("/ajouter")
    public ResponseEntity<Etudiant> ajouterEtudiant(@RequestBody Etudiant etudiant) {
        Etudiant savedEtudiant = etudiantService.ajouterEtudiant(etudiant);
        return ResponseEntity.ok(savedEtudiant);
    }
}

