package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.repositories.EtudiantRepository;
import com.example.demo.repositories.ProfesseurRepository;
import com.example.demo.services.AuthService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ProfesseurRepository professeurRepository;

    @PostMapping("/signup/etudiant")
    public ResponseEntity<Etudiant> signupEtudiant(@RequestBody Etudiant etudiant) {
        Etudiant savedEtudiant = authService.signupEtudiant(etudiant);
        return new ResponseEntity<>(savedEtudiant, HttpStatus.CREATED);
    }

    @PostMapping("/signup/professeur")
    public ResponseEntity<Professeur> signupProfesseur(@RequestBody Professeur professeur) {
        Professeur savedProfesseur = authService.signupProfesseur(professeur);
        return new ResponseEntity<>(savedProfesseur, HttpStatus.CREATED);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        String email = jwtUtil.getEmailFromToken(token);
        if (jwtUtil.validateToken(token, email)) {
            // Vérifier si c'est un étudiant ou un professeur
            Optional<Etudiant> etudiantOpt = etudiantRepository.findByEmail(email);
            if (etudiantOpt.isPresent()) {
                Etudiant etudiant = etudiantOpt.get();
                etudiant.setEmailVerified(true);
                etudiantRepository.save(etudiant);
                return ResponseEntity.ok("Email vérifié avec succès pour l'étudiant");
            }

            Optional<Professeur> professeurOpt = professeurRepository.findByEmail(email);
            if (professeurOpt.isPresent()) {
                Professeur professeur = professeurOpt.get();
                professeur.setEmailVerified(true);
                professeurRepository.save(professeur);
                return ResponseEntity.ok("Email vérifié avec succès pour le professeur");
            }
        }
        return ResponseEntity.badRequest().body("Token invalide ou expiré");
    }
}