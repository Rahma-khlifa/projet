package com.example.demo.controllers;

import com.example.demo.entities.*;
import com.example.demo.repositories.EtudiantRepository;
import com.example.demo.services.IProfesseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/professeurs")
public class ProfesseurController {

    private final IProfesseurService professeurService;
    
    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    public ProfesseurController(IProfesseurService professeurService) {
        this.professeurService = professeurService;
    }

    @GetMapping("/tous")
    public ResponseEntity<List<Professeur>> getAllProfesseurs() {
        List<Professeur> professeurs = professeurService.getAllProfesseurs();
        return new ResponseEntity<>(professeurs, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<Etudiant>> getEtudiants() {
        List<Etudiant> etudiants = etudiantRepository.findByIsEmailVerifiedTrue();
        return new ResponseEntity<>(etudiants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professeur> getProfesseurById(@PathVariable int id) {
        Professeur professeur = professeurService.getProfesseurById(id);
        return new ResponseEntity<>(professeur, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Professeur> createProfesseur(@RequestBody Professeur professeur) {
        Professeur newProfesseur = professeurService.saveProfesseur(professeur);
        return new ResponseEntity<>(newProfesseur, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professeur> updateProfesseur(@PathVariable int id, @RequestBody Professeur professeur) {
        Professeur existingProfesseur = professeurService.getProfesseurById(id);
        professeur.setId(id);
        Professeur updatedProfesseur = professeurService.saveProfesseur(professeur);
        return new ResponseEntity<>(updatedProfesseur, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfesseur(@PathVariable int id) {
        professeurService.deleteProfesseur(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoints spécifiques aux fonctionnalités du professeur
    @PostMapping("/{id}/cours")
    public ResponseEntity<Cours> ajouterCours(
            @PathVariable int id,
            @RequestParam String titre,
            @RequestParam String contenu,
            @RequestParam(required = false) MultipartFile file) throws IOException {
        Cours newCours = professeurService.ajouterCours(id, titre, contenu, file);
        return new ResponseEntity<>(newCours, HttpStatus.CREATED);
    }

    @GetMapping("/problemes")
    public ResponseEntity<List<Probleme>> consulterProblemes() {
        List<Probleme> problemes = professeurService.consulterProblemes();
        return new ResponseEntity<>(problemes, HttpStatus.OK);
    }

    @GetMapping("/{id}/problemes")
    public ResponseEntity<List<Probleme>> consulterProblemes(@PathVariable int id) {
        List<Probleme> problemes = professeurService.consulterProblemes(id);
        return new ResponseEntity<>(problemes, HttpStatus.OK);
    }

    @PostMapping("/{professeurId}/problemes/{problemeId}/reponses")
    public ResponseEntity<Reponse> repondreProbleme(
            @PathVariable int professeurId,
            @PathVariable int problemeId,
            @RequestParam String contenu) {
        Reponse newReponse = professeurService.repondreProbleme(professeurId, problemeId, contenu);
        return new ResponseEntity<>(newReponse, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/rendez-vous")
    public ResponseEntity<RendezVous> organiserRdv(
            @PathVariable int id,
            @RequestParam int etudiantId,
            @RequestParam String sujet) {
        RendezVous newRendezVous = professeurService.organiserRdv(id, etudiantId, sujet);
        return new ResponseEntity<>(newRendezVous, HttpStatus.CREATED);
    }

    @PutMapping("/rendez-vous/{rdvId}/status")
    public ResponseEntity<RendezVous> updateRendezVousStatus(
            @PathVariable Integer rdvId,
            @RequestParam String status) {
        RendezVous rendezVous = professeurService.updateRendezVousStatus(rdvId, status);
        return new ResponseEntity<>(rendezVous, HttpStatus.OK);
    }
    
    @PostMapping("/{id}/annonces")
    public ResponseEntity<Annonce> publierAnnonce(
            @PathVariable int id,
            @RequestParam String titre,
            @RequestParam String contenu) {
        Annonce newAnnonce = professeurService.publierAnnonce(id, titre, contenu);
        return new ResponseEntity<>(newAnnonce, HttpStatus.CREATED);
    }

    @GetMapping("/annonces")
    public ResponseEntity<List<Annonce>> getAllAnnonces() {
        List<Annonce> annonces = professeurService.getAllAnnonces();
        return new ResponseEntity<>(annonces, HttpStatus.OK);
    }

    @GetMapping("/{id}/annonces")
    public ResponseEntity<List<Annonce>> getAnnoncesByProfesseur(@PathVariable int id) {
        List<Annonce> annonces = professeurService.getAnnoncesByProfesseur(id);
        return new ResponseEntity<>(annonces, HttpStatus.OK);
    }

    @GetMapping("/{id}/cours")
    public ResponseEntity<List<Cours>> getCoursByProfesseur(@PathVariable int id) {
        List<Cours> cours = professeurService.getCoursByProfesseur(id);
        return new ResponseEntity<>(cours, HttpStatus.OK);
    }

    @GetMapping("/{id}/rendez-vous")
    public ResponseEntity<List<RendezVous>> getRendezVousByProfesseur(@PathVariable int id) {
        List<RendezVous> rendezVous = professeurService.getRendezVousByProfesseur(id);
        return new ResponseEntity<>(rendezVous, HttpStatus.OK);
    }
}