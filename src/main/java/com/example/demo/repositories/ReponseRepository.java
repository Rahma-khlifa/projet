package com.example.demo.repositories;

import com.example.demo.entities.Reponse;
import com.example.demo.entities.Probleme;
import com.example.demo.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReponseRepository extends JpaRepository<Reponse, Integer> {
    // Find responses by problem
    List<Reponse> findByProbleme(Probleme probleme);
    
    // Find responses by student
    List<Reponse> findByEtudiant(Etudiant etudiant);
    
    // Find responses ordered by date
    List<Reponse> findByProblemeOrderByDateReponseDesc(Probleme probleme);
}