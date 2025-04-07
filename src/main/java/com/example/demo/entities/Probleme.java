package com.example.demo.entities;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "problemes")
public class Probleme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String titre;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "etudiant_id")
    @JsonIgnore // Empêche la boucle infinie
    private Etudiant etudiant;
    
    @OneToMany(mappedBy = "probleme", cascade = CascadeType.ALL)
    private List<Reponse> reponses;
    
    // Constructors
    public Probleme() {
    }
    
    public Probleme(String titre, String description) {
        this.titre = titre;
        this.description = description;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Etudiant getEtudiant() {
        return etudiant;
    }
    
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
    
    public List<Reponse> getReponses() {
        return reponses;
    }
    
    public void setReponses(List<Reponse> reponses) {
        this.reponses = reponses;
    }
}