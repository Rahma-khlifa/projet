package com.example.demo.repositories;

import com.example.demo.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findAllByOrderByDatePublicationDesc();
    List<Article> findByAdminId(int adminId);
    List<Article> findByTitreContainingIgnoreCase(String titre);
}