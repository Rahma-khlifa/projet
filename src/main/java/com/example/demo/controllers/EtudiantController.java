package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.repositories.EtudiantRepository;
import com.example.demo.services.IEtudiantService;
import com.example.demo.services.IProfesseurService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    private final IEtudiantService etudiantService;
    
    @Autowired
    public EtudiantController(IEtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }
    
    @Autowired
    private EtudiantRepository etudiantRepository;
    
    // CRUD de base pour Etudiant
    
    @GetMapping("/tous")
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        List<Etudiant> etudiants = etudiantService.getAllEtudiants();
        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<Etudiant>> getEtudiants() {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Etudiant> ajouterEtudiant(@RequestBody Etudiant etudiant) {
        Etudiant savedEtudiant = etudiantService.saveEtudiant(etudiant);
        return new ResponseEntity<>(savedEtudiant, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable Long id) {
        Etudiant etudiant = etudiantService.getEtudiantById(id);
        return new ResponseEntity<>(etudiant, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Etudiant> updateEtudiant(@PathVariable Long id, @RequestBody Etudiant etudiant) {
        Etudiant existingEtudiant = etudiantService.getEtudiantById(id);
        etudiant.setId(id);
        Etudiant updatedEtudiant = etudiantService.saveEtudiant(etudiant);
        return new ResponseEntity<>(updatedEtudiant, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Fonctionnalités spécifiques à l'étudiant

    // Ajouter un cours
    @PostMapping("/{id}/cours")
    public ResponseEntity<Cours> ajouterCours(
            @PathVariable Long id,
            @RequestParam String titre,
            @RequestParam String contenu,
            @RequestParam(required = false) MultipartFile file) throws IOException {
        Etudiant etudiant = etudiantService.getEtudiantById(id); // Vérifie que l'étudiant existe
        Cours cours = etudiantService.ajouterCours( id, titre, contenu, file);
        etudiant.getCours().add(cours); // Associe le cours à l'étudiant via la relation many-to-many
        etudiantRepository.save(etudiant);
        return new ResponseEntity<>(cours, HttpStatus.CREATED);
    }
    // Consulter tous les cours (déjà dans CoursController)
    @GetMapping("/{id}/cours")
    public ResponseEntity<List<Cours>> getCoursByEtudiant(@PathVariable Long id) {
        Etudiant etudiant = etudiantService.getEtudiantById(id);
        List<Cours> cours = new ArrayList<>(etudiant.getCours()); // Récupère les cours via la relation
        return new ResponseEntity<>(cours, HttpStatus.OK);
    }

    // Consulter un cours par ID
    @GetMapping("/cours/{coursId}")
    public ResponseEntity<Cours> getCoursById(@PathVariable Long coursId) {
        Cours cours = etudiantService.getCoursById(coursId);
        return new ResponseEntity<>(cours, HttpStatus.OK);
    }

    // Consulter les cours par titre
    @GetMapping("/cours/titre/{titre}")
    public ResponseEntity<List<Cours>> getCoursByTitre(@PathVariable String titre) {
        List<Cours> cours = etudiantService.getCoursByTitre(titre);
        return new ResponseEntity<>(cours, HttpStatus.OK);
    }

    // Supprimer un cours
    @DeleteMapping("/cours/{coursId}")
    public ResponseEntity<Void> supprimerCours(@PathVariable Long coursId) {
        etudiantService.supprimerCours(coursId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Publier un problème
    @PostMapping("/{id}/problemes")
    public ResponseEntity<Probleme> publierProbleme(
            @PathVariable Long id,
            @RequestParam String titre,
            @RequestParam String description) {
        Probleme probleme = etudiantService.publierProbleme(id, titre, description);
        return new ResponseEntity<>(probleme, HttpStatus.CREATED);
    }

    // Consulter tous les problèmes
    @GetMapping("/problemes")
    public ResponseEntity<List<Probleme>> getAllProblemes() {
        List<Probleme> problemes = etudiantService.getAllProblemes();
        return new ResponseEntity<>(problemes, HttpStatus.OK);
    }

    // Consulter les problèmes d'un étudiant
    @GetMapping("/{id}/problemes")
    public ResponseEntity<List<Probleme>> getProblemesByEtudiant(@PathVariable Long id) {
        List<Probleme> problemes = etudiantService.getProblemesByEtudiant(id);
        return new ResponseEntity<>(problemes, HttpStatus.OK);
    }

    // Consulter un problème par ID
    @GetMapping("/problemes/{problemeId}")
    public ResponseEntity<Probleme> getProblemeById(@PathVariable int problemeId) {
        Probleme probleme = etudiantService.getProblemeById(problemeId);
        return new ResponseEntity<>(probleme, HttpStatus.OK);
    }

    // Consulter les réponses à un problème
    @GetMapping("/problemes/{problemeId}/reponses")
    public ResponseEntity<List<Reponse>> getReponsesByProbleme(@PathVariable int problemeId) {
        List<Reponse> reponses = etudiantService.getReponsesByProbleme(problemeId);
        return new ResponseEntity<>(reponses, HttpStatus.OK);
    }

    // Ajouter une réponse à un problème
    @PostMapping("/{id}/problemes/{problemeId}/reponses")
    public ResponseEntity<Reponse> addReponseToProbleme(
            @PathVariable Long id,
            @PathVariable int problemeId,
            @RequestParam String contenu) {
        Reponse reponse = etudiantService.publierReponse(id, problemeId, contenu);
        return new ResponseEntity<>(reponse, HttpStatus.CREATED);
    }

    // Demander un rendez-vous
    @PostMapping("/{id}/rendez-vous")
    public ResponseEntity<RendezVous> demanderRdv(
            @PathVariable Long id,
            @RequestParam String nomProfesseur,
            @RequestParam String sujet) {
        RendezVous rendezVous = etudiantService.demanderRdv(id, nomProfesseur, sujet);
        return new ResponseEntity<>(rendezVous, HttpStatus.CREATED);
    }

    // Consulter les rendez-vous d'un étudiant
    @GetMapping("/{id}/rendez-vous")
    public ResponseEntity<List<RendezVous>> getRendezVousByEtudiant(@PathVariable Long id) {
        List<RendezVous> rendezVous = etudiantService.getRendezVousByEtudiant(id);
        return new ResponseEntity<>(rendezVous, HttpStatus.OK);
    }

    // Consulter toutes les annonces
    @GetMapping("/annonces")
    public ResponseEntity<List<Annonce>> getAllAnnonces() {
        List<Annonce> annonces = etudiantService.getAllAnnonces();
        return new ResponseEntity<>(annonces, HttpStatus.OK);
    }

    // Consulter les annonces d'un professeur spécifique
    @GetMapping("/annonces/professeur/{professeurId}")
    public ResponseEntity<List<Annonce>> getAnnoncesByProfesseur(@PathVariable int professeurId) {
        List<Annonce> annonces = etudiantService.getAnnoncesByProfesseur(professeurId);
        return new ResponseEntity<>(annonces, HttpStatus.OK);
    }
}