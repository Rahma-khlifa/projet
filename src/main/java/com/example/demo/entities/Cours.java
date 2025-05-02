package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Peut être changé en int si tu veux respecter strictement le diagramme UML

    private String titre;

    @Column(columnDefinition = "TEXT")
    private String contenu; // Renommé de "description" à "contenu" pour respecter le diagramme UML

    @Lob
    private byte[] fichierPdf; // Pour stocker le fichier PDF

    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private Etudiant auteur; // Relation auteur (un étudiant qui a créé le cours)

    @ManyToOne
    @JoinColumn(name = "professeur_id")
    @JsonIgnore
    private Professeur professeur; // Relation avec un professeur

    @ManyToMany(mappedBy = "cours")
    private List<Etudiant> etudiants = new ArrayList<>(); // Relation partagerCours (many-to-many avec Etudiant)

    // Constructeurs
    public Cours() {
    }

    public Cours(String titre, String contenu, Etudiant auteur, Professeur professeur, byte[] fichierPdf) {
        this.titre = titre;
        this.contenu = contenu;
        this.auteur = auteur;
        this.professeur = professeur;
        this.fichierPdf = fichierPdf;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public byte[] getFichierPdf() {
        return fichierPdf;
    }

    public void setFichierPdf(byte[] fichierPdf) {
        this.fichierPdf = fichierPdf;
    }

    public Etudiant getAuteur() {
        return auteur;
    }

    public void setAuteur(Etudiant auteur) {
        this.auteur = auteur;
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

	public void setDescription(String description) {
		
	}
}