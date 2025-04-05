package com.example.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Cours;
import com.example.demo.entities.Etudiant;

public interface CoursService {

    public  Cours ajouterCours(String titre, String description, MultipartFile file, Long etudiantId) throws IOException;

    public List<Cours> getAllCours();

    public  Cours getCoursById(Long id);

    public   void supprimerCours(Long id);
    public  List<Cours> getCoursByTitre(String titre);


	
}
