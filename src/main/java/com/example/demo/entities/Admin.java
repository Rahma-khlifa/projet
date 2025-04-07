package com.example.demo.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity

public class Admin extends User {
    
	 private String departement;
	 private String poste;
	/**
	 * 
	 */
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param email
	 * @param motDePasse
	 * @param nom
	 */
	public Admin(Long id, String email, String motDePasse, String nom) {
		super(id, email, motDePasse, nom);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param id
	 * @param email
	 * @param motDePasse
	 * @param nom
	 * @param departement
	 * @param poste
	 */
	public Admin(Long id, String email, String motDePasse, String nom, String departement, String poste) {
		super(id, email, motDePasse, nom);
		this.departement = departement;
		this.poste = poste;
	}
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	public String getPoste() {
		return poste;
	}
	public void setPoste(String poste) {
		this.poste = poste;
	}
}


