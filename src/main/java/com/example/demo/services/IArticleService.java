package com.example.demo.services;

import com.example.demo.entities.Article;
import java.util.List;
import java.util.Optional;

public interface IArticleService {
    List<Article> getAllArticles();
    Optional<Article> getArticleById(int id);
    Article saveArticle(Article article);
    void deleteArticle(int id);
    List<Article> getArticlesByAdminId(int adminId);
    List<Article> searchArticlesByTitle(String titre);
    List<Article> getRecentArticles();
}