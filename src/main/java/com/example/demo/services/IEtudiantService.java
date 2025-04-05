package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Etudiant;

@Service
public interface IEtudiantService {
	 Etudiant ajouterEtudiant(Etudiant etudiant);
}
