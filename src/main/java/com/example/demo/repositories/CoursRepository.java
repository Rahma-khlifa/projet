package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Admin;
import com.example.demo.entities.Cours;
@Repository
public interface CoursRepository extends JpaRepository <Cours,Long> {
	List<Cours> findByAuteurId(Long etudiantId);
    List<Cours> findAllByOrderByTitreAsc();
    List<Cours> findByTitre(String titre);
	List<Cours> findByProfesseurId(int professeurId);

}
