package com.example.demo.controllers;

import com.example.demo.services.IEtudiantService;
import com.example.demo.services.IProfesseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class VerificationController {

    @Autowired
    private IEtudiantService etudiantService;

    @Autowired
    private IProfesseurService professeurService;

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        // Vérifier si le jeton appartient à un étudiant
        boolean verifiedByEtudiant = etudiantService.verifyEmailToken(token);
        if (verifiedByEtudiant) {
            return new ResponseEntity<>("Email vérifié avec succès pour l'étudiant !", HttpStatus.OK);
        }

        // Vérifier si le jeton appartient à un professeur
        boolean verifiedByProfesseur = professeurService.verifyEmailToken(token);
        if (verifiedByProfesseur) {
            return new ResponseEntity<>("Email vérifié avec succès pour le professeur !", HttpStatus.OK);
        }

        return new ResponseEntity<>("Jeton de vérification invalide ou expiré.", HttpStatus.BAD_REQUEST);
    }
}