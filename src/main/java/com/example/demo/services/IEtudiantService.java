package com.example.demo.services;

import com.example.demo.entities.Annonce;
import com.example.demo.entities.Cours;
import com.example.demo.entities.Etudiant;
import com.example.demo.entities.Probleme;
import com.example.demo.entities.RendezVous;
import com.example.demo.entities.Reponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface IEtudiantService {
    // User management
    Etudiant saveEtudiant(Etudiant etudiant);
    Etudiant getEtudiantById(Long id);
    Optional<Etudiant> getEtudiantByEmail(String email);
	List<Etudiant> getAllEtudiants();
	void deleteEtudiant(Long id);
    
    // Problem management
    Probleme publierProbleme(Long etudiantId, String titre, String description);
    List<Probleme> getAllProblemes();
    Probleme getProblemeById(int id);
    List<Probleme> getProblemesByEtudiant(Long etudiantId);
    
    // Response management
    Reponse publierReponse(Long etudiantId, int problemeId, String contenu);
    List<Reponse> getReponsesByProbleme(int problemeId);
    Reponse getReponseById(int id);

    // Course management (fusionné depuis CoursService)
    List<Cours> getAllCours();
    Cours getCoursById(Long id);
    void supprimerCours(Long id);
    List<Cours> getCoursByTitre(String titre);
	void partagerCoursAvecEtudiant(Long coursId, Long etudiantId);
	
	// Gestion des rendez-vous
    RendezVous demanderRdv(Long etudiantId, String nomProfesseur,  String sujet);
    List<RendezVous> getRendezVousByEtudiant(Long id);

    // Gestion des annonces (consultation)
    List<Annonce> getAllAnnonces();
    List<Annonce> getAnnoncesByProfesseur(int professeurId);

    //signUp and logIn
    Etudiant signup(Etudiant etudiant);
    Etudiant login(String email, String motDePasse);
    boolean verifyEmailToken(String token);
	Cours ajouterCours(Long etudiantId, String titre, String contenu, MultipartFile file) throws IOException;

	
}