package com.example.demo.entities;

import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reponses")
public class Reponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(columnDefinition = "TEXT")
    private String contenu;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReponse;
    
    @ManyToOne
    @JoinColumn(name = "probleme_id")
    @JsonIgnore // Empêche la boucle infinie
    private Probleme probleme;
    
    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    @JsonIgnore // Empêche la boucle infinie
    private Etudiant etudiant;
    
    @ManyToOne
    @JoinColumn(name = "professeur_id")
    @JsonIgnore
    private Professeur professeur;
    
    // Constructors
    public Reponse() {
        this.dateReponse = new Date();
    }
    
    public Reponse(String contenu) {
        this.contenu = contenu;
        this.dateReponse = new Date();
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getContenu() {
        return contenu;
    }
    
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
    public Date getDateReponse() {
        return dateReponse;
    }
    
    public void setDateReponse(Date dateReponse) {
        this.dateReponse = dateReponse;
    }
    
    public Probleme getProbleme() {
        return probleme;
    }
    
    public void setProbleme(Probleme probleme) {
        this.probleme = probleme;
    }
    
    public Etudiant getEtudiant() {
        return etudiant;
    }
    
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
    
    public Professeur getProfesseur() {
        return professeur;
    }
    
    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }
    
    // Méthode pour savoir si la réponse vient d'un étudiant ou d'un professeur
    public boolean estReponseEtudiant() {
        return this.etudiant != null;
    }
    
    public boolean estReponseProfesseur() {
        return this.professeur != null;
    }
}