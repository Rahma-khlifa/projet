package com.example.demo.repositories;

import com.example.demo.entities.Annonce;
import com.example.demo.entities.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {
    // Trouver toutes les annonces d'un professeur
    List<Annonce> findByProfesseur(Professeur professeur);
    
    // Trouver les dernières annonces (classées par date)
    List<Annonce> findTop10ByOrderByDatePublicationDesc();
    
    // Rechercher des annonces par mot-clé dans le titre
    List<Annonce> findByTitreContaining(String keyword);

	List<Annonce> findByProfesseurId(int professeurId);
}