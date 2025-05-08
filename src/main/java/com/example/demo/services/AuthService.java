package com.example.demo.services;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    public Map<String, String> processAuth(Map<String, String> request) {
        String action = request.get("action");
        String role = request.get("role");
        String email = request.get("email");
        String password = request.get("password");
        String nom = request.get("nom");

        if (action == null || role == null) {
            throw new RuntimeException("Action ou rôle manquant");
        }

        if (action.equalsIgnoreCase("signin")) {
            // Logique de connexion
            if (role.equalsIgnoreCase("etudiant")) {
                Optional<Etudiant> etudiant = etudiantRepository.findByEmail(email);
                if (!etudiant.isPresent()) {
                    throw new RuntimeException("Email invalide");
                }
                Etudiant e = etudiant.get();
                if (!e.getMotDePasse().equals(password)) { // Pas de hachage, comparaison directe
                    throw new RuntimeException("Mot de passe invalide");
                }
                if (!e.isEmailVerified()) {
                    throw new RuntimeException("Email non vérifié");
                }
                String token = jwtUtil.generateToken(email);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Connexion réussie pour étudiant");
                response.put("token", token);
                response.put("id", String.valueOf(e.getId())); // Ajout de l'ID
                return response;
            } else if (role.equalsIgnoreCase("professeur")) {
                Optional<Professeur> professeur = professeurRepository.findByEmail(email);
                if (!professeur.isPresent()) {
                    throw new RuntimeException("Email invalide");
                }
                Professeur p = professeur.get();
                if (!p.getMotDePasse().equals(password)) { // Pas de hachage, comparaison directe
                    throw new RuntimeException("Mot de passe invalide");
                }
                if (!p.isEmailVerified()) {
                    throw new RuntimeException("Email non vérifié");
                }
                String token = jwtUtil.generateToken(email);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Connexion réussie pour professeur");
                response.put("token", token);
                response.put("id", String.valueOf(p.getId())); // Ajout de l'ID
                return response;
            } else {
                throw new RuntimeException("Rôle invalide");
            }
        } else if (action.equalsIgnoreCase("signup")) {
            // Logique d'inscription
            if (nom == null || nom.isEmpty()) {
                throw new RuntimeException("Nom manquant pour l'inscription");
            }

            if (role.equalsIgnoreCase("etudiant")) {
                if (etudiantRepository.findByEmail(email).isPresent()) {
                    throw new RuntimeException("Cet email est déjà utilisé par un étudiant");
                }
                if (professeurRepository.findByEmail(email).isPresent()) {
                    throw new RuntimeException("Cet email est déjà utilisé par un professeur");
                }
                Etudiant etudiant = new Etudiant();
                etudiant.setNom(nom);
                etudiant.setEmail(email);
                etudiant.setMotDePasse(password); // Pas de hachage
                etudiant.setEmailVerified(false);
                Etudiant savedEtudiant = etudiantRepository.save(etudiant);

                // Générer un JWT pour la vérification email
                String verificationToken = jwtUtil.generateToken(email);
                String verificationLink = "http://localhost:8080/api/auth/verify-email?token=" + verificationToken;
                emailService.sendVerificationEmail(email, verificationLink);

                return Map.of("message", "Inscription réussie pour l'étudiant. Vérifiez votre email pour activer votre compte.");
            } else if (role.equalsIgnoreCase("professeur")) {
                if (etudiantRepository.findByEmail(email).isPresent()) {
                    throw new RuntimeException("Cet email est déjà utilisé par un étudiant");
                }
                if (professeurRepository.findByEmail(email).isPresent()) {
                    throw new RuntimeException("Cet email est déjà utilisé par un professeur");
                }
                Professeur professeur = new Professeur();
                professeur.setNom(nom);
                professeur.setEmail(email);
                professeur.setMotDePasse(password); // Pas de hachage
                professeur.setEmailVerified(false);
                Professeur savedProfesseur = professeurRepository.save(professeur);

                // Générer un JWT pour la vérification email
                String verificationToken = jwtUtil.generateToken(email);
                String verificationLink = "http://localhost:8080/api/auth/verify-email?token=" + verificationToken;
                emailService.sendVerificationEmail(email, verificationLink);

                return Map.of("message", "Inscription réussie pour le professeur. Vérifiez votre email pour activer votre compte.");
            } else {
                throw new RuntimeException("Rôle invalide");
            }
        } else {
            throw new RuntimeException("Action invalide (doit être 'signin' ou 'signup')");
        }
    }

    public List<Etudiant> getVerifiedEtudiants() {
        return etudiantRepository.findByIsEmailVerifiedTrue();
    }

    public List<Professeur> getVerifiedProfesseurs() {
        return professeurRepository.findByIsEmailVerifiedTrue();
    }
}