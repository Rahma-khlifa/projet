package com.example.demo.services;

import com.example.demo.entities.Annonce;
import com.example.demo.entities.Cours;
import com.example.demo.entities.Etudiant;
import com.example.demo.entities.Probleme;
import com.example.demo.entities.Professeur;
import com.example.demo.entities.RendezVous;
import com.example.demo.entities.Reponse;
import com.example.demo.repositories.AnnonceRepository;
import com.example.demo.repositories.CoursRepository;
import com.example.demo.repositories.EtudiantRepository;
import com.example.demo.repositories.ProblemeRepository;
import com.example.demo.repositories.ProfesseurRepository;
import com.example.demo.repositories.RendezVousRepository;
import com.example.demo.repositories.ReponseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EtudiantServiceImpl implements IEtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;
    
    @Autowired
    private ProblemeRepository problemeRepository;
    
    @Autowired
    private ReponseRepository reponseRepository;

    @Autowired
    private CoursRepository coursRepository; // Ajout de CoursRepository
    
    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private AnnonceRepository annonceRepository;

    // User management implementations
    
    @Override
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }
    
    @Override
    public Etudiant saveEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }
    
    @Override
    public Etudiant getEtudiantById(Long id) {
        return etudiantRepository.findById(id).orElseThrow(() -> new RuntimeException("Etudiant non trouvé avec id : " + id));
    }
    
    @Override
    public Optional<Etudiant> getEtudiantByEmail(String email) {
        return etudiantRepository.findByEmail(email);
    }
    @Override
    public void deleteEtudiant(Long id) {
        Etudiant etudiant = getEtudiantById(id);
        etudiantRepository.delete(etudiant);
    }
    
    // Problem management implementations
    @Override
    @Transactional
    public Probleme publierProbleme(Long etudiantId, String titre, String description) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Etudiant non trouvé avec id : " + etudiantId));
        
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
    public List<Probleme> getProblemesByEtudiant(Long etudiantId) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Etudiant non trouvé avec id : " + etudiantId));
        
        return problemeRepository.findByEtudiant(etudiant);
    }
    
    // Response management implementations
    @Override
    @Transactional
    public Reponse publierReponse(Long etudiantId, int problemeId, String contenu) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Etudiant non trouvé avec id : " + etudiantId));
            
        Probleme probleme = problemeRepository.findById(problemeId)
            .orElseThrow(() -> new RuntimeException("Problème non trouvé avec id : " + problemeId));
        
        Reponse reponse = new Reponse(contenu);
        reponse.setEtudiant(etudiant);
        reponse.setProbleme(probleme);
        
        return reponseRepository.save(reponse);
    }
    
    @Override
    public List<Reponse> getReponsesByProbleme(int problemeId) {
        Probleme probleme = problemeRepository.findById(problemeId)
            .orElseThrow(() -> new RuntimeException("Problème non trouvé avec id : " + problemeId));
            
        return reponseRepository.findByProblemeOrderByDateReponseDesc(probleme);
    }
    
    @Override
    public Reponse getReponseById(int id) {
        return reponseRepository.findById(id).orElse(null);
    }

    // Course management implementations (fusionnées depuis CoursServiceImpl)
    @Override
    @Transactional
    public Cours ajouterCours(Long etudiantId, String titre, String contenu, MultipartFile file) throws IOException {
        Cours cours = new Cours();
        cours.setTitre(titre);
        cours.setContenu(contenu); // Note : utilise "contenu" au lieu de "description" pour respecter ton modèle
        if (file != null && !file.isEmpty()) {
            byte[] fileContent = file.getBytes();
            cours.setFichierPdf(fileContent);
        }
        Cours savedCours = coursRepository.save(cours);

        // Associer le cours à l'étudiant via la relation many-to-many
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec id : " + etudiantId));
        etudiant.getCours().add(savedCours);
        etudiantRepository.save(etudiant);

        return savedCours;
    }

    @Override
    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    @Override
    public Cours getCoursById(Long id) {
        return coursRepository.findById(id).orElseThrow(() -> new RuntimeException("Cours non trouvé avec id : " + id));
    }

    @Override
    public void supprimerCours(Long id) {
        coursRepository.deleteById(id);
    }

    @Override
    public List<Cours> getCoursByTitre(String titre) {
        return coursRepository.findByTitre(titre);
    }

    @Override
    @Transactional
    public void partagerCoursAvecEtudiant(Long coursId, Long etudiantId) {
        Cours cours = coursRepository.findById(coursId)
            .orElseThrow(() -> new RuntimeException("Cours non trouvé avec id : " + coursId));
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Etudiant non trouvé avec id : " + etudiantId));
        
        // Ajouter le cours à la liste des cours de l'étudiant (relation partagerCours)
        etudiant.getCours().add(cours);
        etudiantRepository.save(etudiant);
    }
    
    @Override
    @Transactional
    public RendezVous demanderRdv(Long etudiantId, String nomProfesseur, String sujet) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé avec id : " + etudiantId));
        Professeur professeur = professeurRepository.findByNom(nomProfesseur)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec le nom : " + nomProfesseur));
        RendezVous rdv = new RendezVous();
        rdv.setDate(LocalDateTime.now()); // Date actuelle
        rdv.setSujet(sujet);
        rdv.setEtudiant(etudiant);
        rdv.setProfesseur(professeur);
        rdv.setStatus("EN_ATTENTE");
        return rendezVousRepository.save(rdv);
    }

    @Override
    public List<RendezVous> getRendezVousByEtudiant(Long id) {
        return rendezVousRepository.findByEtudiantId(id);
    }

    @Override
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    @Override
    public List<Annonce> getAnnoncesByProfesseur(int professeurId) {
        return annonceRepository.findByProfesseurId(professeurId);
    }

	@Override
	public Etudiant signup(Etudiant etudiant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Etudiant login(String email, String motDePasse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyEmailToken(String token) {
		// TODO Auto-generated method stub
		return false;
	}

    
}