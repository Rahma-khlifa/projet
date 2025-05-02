package com.example.demo;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo"})
public class ProjetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetApplication.class, args);
        System.out.println("\n===================================");
        System.out.println("Application démarrée avec succès!");
        System.out.println("API disponible à: http://localhost:8080/api");
        System.out.println("===================================\n");
    }

    @Bean
    CommandLineRunner initData(
            EtudiantRepository etudiantRepository,
            ProfesseurRepository professeurRepository,
            CoursRepository coursRepository,
            ProblemeRepository problemeRepository,
            ReponseRepository reponseRepository,
            RendezVousRepository rendezVousRepository,
            AnnonceRepository annonceRepository) {
        return args -> {
            // Vérifier si la base de données est vide pour éviter d'ajouter des doublons
            if (etudiantRepository.count() == 0 && professeurRepository.count() == 0) {
                // Créer des étudiants
                Etudiant e1 = new Etudiant("Licence 1", "Informatique");
                e1.setNom("Etudiant1");
                e1.setEmail("etudiant1@example.com");
                e1.setMotDePasse("pass123");
                etudiantRepository.save(e1);

                Etudiant e2 = new Etudiant("Licence 2", "Mathématiques");
                e2.setNom("Etudiant2");
                e2.setEmail("etudiant2@example.com");
                e2.setMotDePasse("pass456");
                etudiantRepository.save(e2);

                System.out.println("✅ Étudiants ajoutés à la base de données !");

                // Créer un professeur
                Professeur p1 = new Professeur();
                p1.setNom("Professeur1");
                p1.setEmail("prof1@example.com");
                p1.setMotDePasse("profpass");
                p1.setSpecialite("Informatique");
                professeurRepository.save(p1);

                System.out.println("✅ Professeur ajouté à la base de données !");

                // Créer un cours par l'étudiant e1
                Cours cours1 = new Cours();
                cours1.setTitre("Introduction à Java");
                cours1.setContenu("Les bases de la programmation en Java.");
                cours1.setAuteur(e1);
                cours1.setProfesseur(p1);
                coursRepository.save(cours1);

                // Partager le cours avec e2
                e2.ajouterCours(cours1);
                etudiantRepository.save(e2);

                System.out.println("✅ Cours ajouté et partagé avec Etudiant2 !");

                // Créer un cours par le professeur p1
                Cours cours2 = new Cours();
                cours2.setTitre("Structures de données");
                cours2.setContenu("Introduction aux structures de données.");
                cours2.setProfesseur(p1);
                coursRepository.save(cours2);

                System.out.println("✅ Cours ajouté par le professeur !");

                // Créer un problème par e1
                Probleme probleme1 = e1.publierProbleme("Problème avec les boucles", "Je n’arrive pas à faire fonctionner une boucle for.");
                probleme1.setEtudiant(e1);
                problemeRepository.save(probleme1);

                System.out.println("✅ Problème publié par Etudiant1 !");

                // Réponse au problème par e2
                Reponse reponse1 = e2.repondreProbleme(probleme1, "Essaie d’utiliser une boucle while à la place.");
                reponse1.setEtudiant(e2);
                reponseRepository.save(reponse1);

                System.out.println("✅ Réponse ajoutée par Etudiant2 !");

                // Réponse au problème par le professeur p1
                Reponse reponse2 = p1.repondreProbleme(probleme1, "Vérifie l’initialisation de ton compteur dans la boucle for.");
                reponse2.setProfesseur(p1);
                reponseRepository.save(reponse2);

                System.out.println("✅ Réponse ajoutée par le professeur !");

             // Organiser un rendez-vous entre e1 et p1
                RendezVous rdv1 = e1.demanderRdv(p1, "2025-05-01T10:00", "Révision Java");
                rdv1.setStatus("PLANIFIÉ"); // Définir explicitement le status
                rendezVousRepository.save(rdv1);

                System.out.println("✅ Rendez-vous organisé entre Etudiant1 et Professeur1 !");

                // Publier une annonce par p1
                Annonce annonce1 = p1.publierAnnonce("Cours annulé", "Le cours de demain est annulé.");
                annonceRepository.save(annonce1);

                System.out.println("✅ Annonce publiée par le professeur !");

                // Afficher un résumé des données ajoutées
                System.out.println("\n===== Résumé des données ajoutées =====");
                System.out.println("Nombre d'étudiants : " + etudiantRepository.count());
                System.out.println("Nombre de professeurs : " + professeurRepository.count());
                System.out.println("Nombre de cours : " + coursRepository.count());
                System.out.println("Nombre de problèmes : " + problemeRepository.count());
                System.out.println("Nombre de réponses : " + reponseRepository.count());
                System.out.println("Nombre de rendez-vous : " + rendezVousRepository.count());
                System.out.println("Nombre d'annonces : " + annonceRepository.count());
                System.out.println("=====================================");
            } else {
                System.out.println("⚠️ La base de données contient déjà des données. Initialisation ignorée.");
            }
        };
    }
}