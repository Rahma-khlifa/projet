package com.example.demo.services;

import com.example.demo.entities.Etudiant;
import com.example.demo.entities.Probleme;
import com.example.demo.entities.Reponse;
import com.example.demo.repositories.EtudiantRepository;
import com.example.demo.repositories.ProblemeRepository;
import com.example.demo.repositories.ReponseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EtudiantServiceImpl implements IEtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;
    
    @Autowired
    private ProblemeRepository problemeRepository;
    
    @Autowired
    private ReponseRepository reponseRepository;
    
    // User management implementations
    @Override
    public Etudiant saveEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }
    
    @Override
    public Etudiant getEtudiantById(int id) {
        return etudiantRepository.findById(id).orElse(null);
    }
    
    @Override
    public Etudiant getEtudiantByEmail(String email) {
        // Assuming you've added this method to your repository
        return etudiantRepository.findByEmail(email);
    }
    
    // Problem management implementations
    @Override
    @Transactional
    public Probleme publierProbleme(int etudiantId, String titre, String description) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Etudiant not found with id: " + etudiantId));
        
        Probleme probleme = new Probleme(titre, description);
        probleme.setEtudiant(etudiant);
        
        return problemeRepository.save(probleme);
    }
    
    @Override
    public List<Probleme> getAllProblemes() {
        return problemeRepository.findAll();
    }
    
    @Override
    public Probleme getProblemeById(int id) {
        return problemeRepository.findById(id).orElse(null);
    }
    
    @Override
    public List<Probleme> getProblemesByEtudiant(int etudiantId) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Etudiant not found with id: " + etudiantId));
        
        return problemeRepository.findByEtudiant(etudiant);
    }
    
    // Response management implementations
    @Override
    @Transactional
    public Reponse publierReponse(int etudiantId, int problemeId, String contenu) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Etudiant not found with id: " + etudiantId));
            
        Probleme probleme = problemeRepository.findById(problemeId)
            .orElseThrow(() -> new RuntimeException("Probleme not found with id: " + problemeId));
        
        Reponse reponse = new Reponse(contenu);
        reponse.setEtudiant(etudiant);
        reponse.setProbleme(probleme);
        
        return reponseRepository.save(reponse);
    }
    
    @Override
    public List<Reponse> getReponsesByProbleme(int problemeId) {
        Probleme probleme = problemeRepository.findById(problemeId)
            .orElseThrow(() -> new RuntimeException("Probleme not found with id: " + problemeId));
            
        return reponseRepository.findByProblemeOrderByDateReponseDesc(probleme);
    }
    
    @Override
    public Reponse getReponseById(int id) {
        return reponseRepository.findById(id).orElse(null);
    }
}