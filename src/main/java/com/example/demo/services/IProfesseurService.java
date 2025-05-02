package com.example.demo.services;

import com.example.demo.entities.Annonce;
import com.example.demo.entities.Cours;
import com.example.demo.entities.Professeur;
import com.example.demo.entities.Probleme;
import com.example.demo.entities.RendezVous;
import com.example.demo.entities.Reponse;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IProfesseurService {
    Professeur saveProfesseur(Professeur professeur);
    Professeur getProfesseurById(int id);
    List<Professeur> getAllProfesseurs(); // Ajout
    void deleteProfesseur(int id); // Ajout

    Cours ajouterCours(int professeurId, String titre, String contenu, MultipartFile file) throws IOException;
    List<Probleme> consulterProblemes();
    List<Probleme> consulterProblemes(int professeurId); // Ajout pour l'endpoint spécifique
    Reponse repondreProbleme(int professeurId, int problemeId, String contenu);
    Annonce publierAnnonce(int professeurId, String titre, String contenu);
    List<Annonce> getAllAnnonces();
    List<Annonce> getAnnoncesByProfesseur(int professeurId); // Ajout
    List<Cours> getCoursByProfesseur(int professeurId); // Ajout
    List<RendezVous> getRendezVousByProfesseur(int professeurId);
    RendezVous organiserRdv(int professeurId, int etudiantId, String sujet);
    RendezVous updateRendezVousStatus(Integer rdvId, String status);
}