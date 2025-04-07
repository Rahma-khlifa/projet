package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Etudiant extends User{
    
    private String niveauEtude;
    private String filiere;
    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL)
    private List<Cours> coursAjoutes = new ArrayList<>();

	/**
	 * 
	 */
	public Etudiant() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param niveauEtude
	 * @param filiere
	 */
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
  
}