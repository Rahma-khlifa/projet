package com.example.demo.repositories;

import com.example.demo.entities.RendezVous;
import com.example.demo.entities.Professeur;
import com.example.demo.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Date;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Integer> {
    // Trouver les rendez-vous par professeur
    List<RendezVous> findByProfesseur(Professeur professeur);
    
    // Trouver les rendez-vous par étudiant
    List<RendezVous> findByEtudiant(Etudiant etudiant);
    
    // Trouver les rendez-vous par date
    List<RendezVous> findByDateBetween(Date debut, Date fin);
    
    // Trouver les rendez-vous par statut
    List<RendezVous> findByStatus(String status);
    
    // Trouver les rendez-vous entre un professeur et un étudiant
    List<RendezVous> findByProfesseurAndEtudiant(Professeur professeur, Etudiant etudiant);

	List<RendezVous> findByProfesseurId(int professeurId);

	List<RendezVous> findByEtudiantId(Long id);
}