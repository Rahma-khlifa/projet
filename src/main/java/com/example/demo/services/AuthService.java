package com.example.demo.services;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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
    public Etudiant signupEtudiant(Etudiant etudiant) {
        if (etudiantRepository.findByEmail(etudiant.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé par un étudiant");
        }
        if (professeurRepository.findByEmail(etudiant.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé par un professeur");
        }
        etudiant.setEmailVerified(false);
        Etudiant savedEtudiant = etudiantRepository.save(etudiant);

        // Générer un JWT pour la vérification email
        String verificationToken = jwtUtil.generateToken(etudiant.getEmail());
        String verificationLink = "http://localhost:8080/api/auth/verify-email?token=" + verificationToken;

        // Envoyer l'email avec le lien de vérification
        emailService.sendVerificationEmail(etudiant.getEmail(), verificationLink);

        return savedEtudiant;
    }

    @Transactional
    public Professeur signupProfesseur(Professeur professeur) {
        if (etudiantRepository.findByEmail(professeur.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé par un étudiant");
        }
        if (professeurRepository.findByEmail(professeur.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé par un professeur");
        }
        professeur.setEmailVerified(false);
        Professeur savedProfesseur = professeurRepository.save(professeur);

        // Générer un JWT pour la vérification email
        String verificationToken = jwtUtil.generateToken(professeur.getEmail());
        String verificationLink = "http://localhost:8080/api/auth/verify-email?token=" + verificationToken;

        // Envoyer l'email avec le lien de vérification
        emailService.sendVerificationEmail(professeur.getEmail(), verificationLink);

        return savedProfesseur;
    }
    
    public java.util.List<Etudiant> getVerifiedEtudiants() {
        return etudiantRepository.findByIsEmailVerifiedTrue();
    }

    public java.util.List<Professeur> getVerifiedProfesseurs() {
        return professeurRepository.findByIsEmailVerifiedTrue();
    }
}