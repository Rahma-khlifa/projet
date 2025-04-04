package com.example.demo.repositories;

import com.example.demo.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Integer> {
    // Find student by email
    Etudiant findByEmail(String email);
}