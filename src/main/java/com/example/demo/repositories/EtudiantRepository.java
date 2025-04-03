package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entities.Etudiant;


public interface EtudiantRepository extends JpaRepository <Etudiant,Long>  {

}
