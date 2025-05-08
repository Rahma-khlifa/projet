package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.repositories.EtudiantRepository;
import com.example.demo.repositories.ProfesseurRepository;
import com.example.demo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ProfesseurRepository professeurRepository;

    @PostMapping("/process")
    public ResponseEntity<?> processAuth(@RequestBody Map<String, String> request) {
        try {
            Map<String, String> response = authService.processAuth(request);
            if (request.get("action").equalsIgnoreCase("signup")) {
                return ResponseEntity.status(HttpStatus.CREATED).body(response.get("message"));
            }
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.out.println("Erreur: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        String email = jwtUtil.getEmailFromToken(token);
        if (jwtUtil.validateToken(token, email)) {
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