package com.example.demo.entities;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "annonces")
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titre;
    private String contenu;
    private LocalDate datePublication;

    @ManyToOne
    @JoinColumn(name = "professeur_id")
    private Professeur professeur;

    public Annonce() {
    }

    public Annonce(String titre, String contenu) {
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = LocalDate.now();
    }

    // Getters et setters
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

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }
}