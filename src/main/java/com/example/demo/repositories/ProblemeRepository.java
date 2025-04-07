package com.example.demo.repositories;

import com.example.demo.entities.Probleme;
import com.example.demo.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemeRepository extends JpaRepository<Probleme, Integer> {
    // Find problems by student
    List<Probleme> findByEtudiant(Etudiant etudiant);
    
    // Find problems by title containing keyword
    List<Probleme> findByTitreContaining(String keyword);
    
    // Find latest problems
    List<Probleme> findTop10ByOrderByIdDesc();
}