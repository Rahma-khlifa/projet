package com.example.demo.entities;


import java.util.List;

import jakarta.persistence.*;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;


@Entity
@Table(name = "etudiants")
public class Etudiant extends User{
    
    private String niveauEtude;
    private String filiere;

    
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<Probleme> problemes;
    
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    private List<Reponse> reponses;

    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL)
    private List<Cours> coursAjoutes = new ArrayList<>();


	public Etudiant() {
		super();

	}
	public Etudiant(String niveauEtude, String filiere) {
		super();
		this.niveauEtude = niveauEtude;
		this.filiere = filiere;
	}

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
	
	 // Getters and Setters
    public List<Probleme> getProblemes() {
        return problemes;
    }
    
    public void setProblemes(List<Probleme> problemes) {
        this.problemes = problemes;
    }
    
    public List<Reponse> getReponses() {
        return reponses;
    }
    
    public void setReponses(List<Reponse> reponses) {
        this.reponses = reponses;
    }
    
    public Probleme publierProbleme(String titre, String description) {
        Probleme probleme = new Probleme(titre, description);
        probleme.setEtudiant(this);
        return probleme;
    }
    
    public Reponse repondreProbleme(Probleme probleme, String contenu) {
        Reponse reponse = new Reponse(contenu);
        reponse.setEtudiant(this);
        reponse.setProbleme(probleme);
        return reponse;
    }
  
}