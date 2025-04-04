package com.example.demo.services;

import com.example.demo.entities.Etudiant;
import com.example.demo.entities.Probleme;
import com.example.demo.entities.Reponse;

import java.util.List;

public interface IEtudiantService {
    // User management
    Etudiant saveEtudiant(Etudiant etudiant);
    Etudiant getEtudiantById(int id);
    Etudiant getEtudiantByEmail(String email);
    
    // Problem management
    Probleme publierProbleme(int etudiantId, String titre, String description);
    List<Probleme> getAllProblemes();
    Probleme getProblemeById(int id);
    List<Probleme> getProblemesByEtudiant(int etudiantId);
    
    // Response management
    Reponse publierReponse(int etudiantId, int problemeId, String contenu);
    List<Reponse> getReponsesByProbleme(int problemeId);
    Reponse getReponseById(int id);
}