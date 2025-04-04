package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.entities.Etudiant;
import com.example.demo.repositories.EtudiantRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo"})
public class ProjetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetApplication.class, args);
        System.out.println("\n===================================");
        System.out.println("Application démarrée avec succès!");
        System.out.println("API disponible à: http://localhost:8080/api/problemes");
        System.out.println("===================================\n");
        
    }

    @Bean
    CommandLineRunner initData(EtudiantRepository etudiantRepository) {
        return args -> {
            if (etudiantRepository.count() == 1) {
            	Etudiant e = new Etudiant();
                e.setNom("Probleme");
                e.setEmail("probleme@example.com"); 
                e.setMotDePasse("1235");
                etudiantRepository.save(e);

                Etudiant e2 = new Etudiant();
                e2.setNom("Response");
                e2.setEmail("response@example.com"); 
                e2.setMotDePasse("1236");
                etudiantRepository.save(e2);
                System.out.println("✅ Étudiants ajoutés à la base de données !");
            }
        };
    }
}