package com.example.demo.services;

import com.example.demo.entities.Annonce;
import com.example.demo.entities.Cours;
import com.example.demo.entities.Etudiant;
import com.example.demo.entities.Professeur;
import com.example.demo.entities.Probleme;
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

@Service
public class ProfesseurServiceImpl implements IProfesseurService {

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private CoursRepository coursRepository;

    @Autowired
    private ProblemeRepository problemeRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private ReponseRepository reponseRepository;

    @Override
    public Professeur saveProfesseur(Professeur professeur) {
        return professeurRepository.save(professeur);
    }

    @Override
    public Professeur getProfesseurById(int id) {
        return professeurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec id : " + id));
    }


    @Override
    public List<Professeur> getAllProfesseurs() {
        return professeurRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteProfesseur(int id) {
        Professeur professeur = professeurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec id : " + id));
        professeurRepository.delete(professeur);
    }

    @Override
    @Transactional
    public Cours ajouterCours(int professeurId, String titre, String contenu, MultipartFile file) throws IOException {
        Professeur professeur = professeurRepository.findById(professeurId)
            .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec id : " + professeurId));

        Cours cours = new Cours();
        cours.setTitre(titre);
        cours.setContenu(contenu);
        if (file != null && !file.isEmpty()) {
            byte[] fileContent = file.getBytes();
            cours.setFichierPdf(fileContent);
        }
        cours.setProfesseur(professeur);
        return coursRepository.save(cours);
    }

    @Override
    public List<Probleme> consulterProblemes() {
        return problemeRepository.findAll();
    }

    @Override
    public List<Probleme> consulterProblemes(int professeurId) {
        Professeur professeur = professeurRepository.findById(professeurId)
            .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec id : " + professeurId));
        return problemeRepository.findAll(); // Pour l'instant, on renvoie tous les problèmes
    }

    @Override
    @Transactional
    public Reponse repondreProbleme(int professeurId, int problemeId, String contenu) {
        Professeur professeur = professeurRepository.findById(professeurId)
            .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec id : " + professeurId));
        Probleme probleme = problemeRepository.findById(problemeId)
            .orElseThrow(() -> new RuntimeException("Problème non trouvé avec id : " + problemeId));

        Reponse reponse = new Reponse(contenu);
        reponse.setProfesseur(professeur);
        reponse.setProbleme(probleme);
        return reponseRepository.save(reponse);
    }

    @Override
    @Transactional
    public RendezVous organiserRdv(int professeurId, int etudiantId, String sujet) {
        Professeur professeur = professeurRepository.findById(professeurId)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec id : " + professeurId));
        Etudiant etudiant = etudiantRepository.findById((long) etudiantId)
                .orElseThrow(() -> new RuntimeException("Etudiant non trouvé avec id : " + etudiantId));
        RendezVous rdv = new RendezVous();
        rdv.setDate(LocalDateTime.now()); // Date actuelle
        rdv.setSujet(sujet);
        rdv.setEtudiant(etudiant);
        rdv.setProfesseur(professeur);
        rdv.setStatus("PLANIFIÉ");
        return rendezVousRepository.save(rdv);
    }

    @Override
    @Transactional
    public RendezVous updateRendezVousStatus(Integer rdvId, String status) {
        RendezVous rendezVous = rendezVousRepository.findById(rdvId)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec id : " + rdvId));
        if (!status.equals("ACCEPTÉ") && !status.equals("REJETÉ")) {
            throw new IllegalArgumentException("Le statut doit être ACCEPTÉ ou REJETÉ");
        }
        rendezVous.setStatus(status);
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    @Transactional
    public Annonce publierAnnonce(int professeurId, String titre, String contenu) {
        Professeur professeur = professeurRepository.findById(professeurId)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec id : " + professeurId));

        Annonce annonce = new Annonce(titre, contenu);
        annonce.setProfesseur(professeur);
        return annonceRepository.save(annonce);
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
    public List<Cours> getCoursByProfesseur(int professeurId) {
        return coursRepository.findByProfesseurId(professeurId);
    }

    @Override
    public List<RendezVous> getRendezVousByProfesseur(int professeurId) {
        return rendezVousRepository.findByProfesseurId(professeurId);
    }

	@Override
	public Professeur signup(Professeur professeur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Professeur login(String email, String motDePasse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyEmailToken(String token) {
		// TODO Auto-generated method stub
		return false;
	}
}