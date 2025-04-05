package com.example.demo.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.repositories.EtudiantRepository;
import com.example.demo.entities.Cours;
import com.example.demo.entities.Etudiant;
import com.example.demo.repositories.CoursRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoursServiceImpl implements CoursService {
	@Autowired
    private EtudiantRepository etudiantRepository;
	@Autowired
    private CoursRepository coursRepository;

    @Override
    public Cours ajouterCours(String titre, String description, MultipartFile file, Long etudiantId) throws IOException {
        // Simulate saving the file and returning a Cours object
        byte[] fileContent = file.getBytes();
        
        Cours cours = new Cours();
        cours.setTitre(titre);
        cours.setDescription(description);
        cours.setFichierPdf(fileContent);
        Etudiant etudiant=etudiantRepository.findById(etudiantId).get();
        cours.setAuteur(etudiant);

        return coursRepository.save(cours);
    }

    @Override
    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    @Override
    public Cours getCoursById(Long id) {
        return coursRepository.findById(id).get();
    }
   
	
	@Override
	public void supprimerCours(Long id) {
		coursRepository.deleteById(id);
	}

	public List <Cours> getCoursByTitre(String titre) {
		return coursRepository.findByTitre(titre);
	}
	
    
}
