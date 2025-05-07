package com.example.demo.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Etudiant;


@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    // Find student by email
	Optional<Etudiant> findByEmail(String email);
	java.util.List<Etudiant> findByIsEmailVerifiedTrue();
}

