package com.example.demo.repositories;

import com.example.demo.entities.Professeur;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur, Integer> {
    // Trouver un professeur par email
    Professeur findByEmail(String email);
    Optional<Professeur> findByNom(String nom);
    
    // Trouver des professeurs par spécialité
    java.util.List<Professeur> findBySpecialite(String specialite);
}