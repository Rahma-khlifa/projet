package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String titre;
    private String description;
    
    @ManyToOne
    private Etudiant auteur;
    
    @Lob
    private byte[] fichierPdf;
    
  
 
	/**
	 * 
	 */
	public Cours() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param titre
	 * @param description
	 * @param auteur
	 * @param fichierPdf
	 * @param nomFichier
	 * @param typeMime
	 */
	public Cours(Long id, String titre, String description, Etudiant auteur, byte[] fichierPdf, String nomFichier,
			String typeMime) {
		super();
		this.id = id;
		this.titre = titre;
		this.description = description;
		this.auteur = auteur;
		this.fichierPdf = fichierPdf;
	
		
	}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Etudiant getAuteur() {
		return auteur;
	}
	public void setAuteur(Etudiant auteur) {
		this.auteur = auteur;
	}
	public byte[] getFichierPdf() {
		return fichierPdf;
	}
	public void setFichierPdf(byte[] fichierPdf) {
		this.fichierPdf = fichierPdf;
	}
	
	
    
    // Getters/Setters
}