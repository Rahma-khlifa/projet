package com.example.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Etudiant;


@Repository

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    // Find student by email
    Etudiant findByEmail(String email);
}

