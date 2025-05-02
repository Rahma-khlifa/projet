package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "etudiants")
public class Etudiant extends User {
    
    private String niveauEtude;
    private String filiere;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<Probleme> problemes = new ArrayList<>();

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<Reponse> reponses = new ArrayList<>();

    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL)
    private List<Cours> coursAjoutes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "etudiant_cours",
        joinColumns = @JoinColumn(name = "etudiant_id"),
        inverseJoinColumns = @JoinColumn(name = "cours_id")
    )
    private List<Cours> cours = new ArrayList<>(); // Relation partagerCours

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<RendezVous> rendezVous = new ArrayList<>();

    public Etudiant() {
        super();
    }

    public Etudiant(String niveauEtude, String filiere) {
        super();
        this.niveauEtude = niveauEtude;
        this.filiere = filiere;
    }

    // Getters et setters pour niveauEtude et filiere
    public String getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    // Getters et setters pour problemes
    public List<Probleme> getProblemes() {
        return problemes;
    }

    public void setProblemes(List<Probleme> problemes) {
        this.problemes = problemes;
    }

    // Getters et setters pour reponses
    public List<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(List<Reponse> reponses) {
        this.reponses = reponses;
    }

    // Getters et setters pour coursAjoutes (relation auteur)
    public List<Cours> getCoursAjoutes() {
        return coursAjoutes;
    }

    public void setCoursAjoutes(List<Cours> coursAjoutes) {
        this.coursAjoutes = coursAjoutes;
    }

    // Getters et setters pour cours (relation partagerCours)
    public List<Cours> getCours() {
        return cours;
    }

    public void setCours(List<Cours> cours) {
        this.cours = cours;
    }

    // Getters et setters pour rendezVous
    public List<RendezVous> getRendezVous() {
        return rendezVous;
    }

    public void setRendezVous(List<RendezVous> rendezVous) {
        this.rendezVous = rendezVous;
    }

    // Méthodes utilitaires
    public Probleme publierProbleme(String titre, String description) {
        Probleme probleme = new Probleme(titre, description);
        probleme.setEtudiant(this);
        this.problemes.add(probleme); // Maintenir la relation bidirectionnelle
        return probleme;
    }

    public Reponse repondreProbleme(Probleme probleme, String contenu) {
        Reponse reponse = new Reponse(contenu);
        reponse.setEtudiant(this);
        reponse.setProbleme(probleme);
        this.reponses.add(reponse); // Maintenir la relation bidirectionnelle
        return reponse;
    }

    public void ajouterCours(Cours cours) {
        this.cours.add(cours);
        cours.getEtudiants().add(this); // Maintenir la relation bidirectionnelle
    }

	public RendezVous demanderRdv(Professeur p1, String string, String string2) {
		return null;
	}

}