package com.example.demo.services;

import com.example.demo.entities.Article;
import com.example.demo.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements IArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Optional<Article> getArticleById(int id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(int id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> getArticlesByAdminId(int adminId) {
        return articleRepository.findByAdminId(adminId);
    }

    @Override
    public List<Article> searchArticlesByTitle(String titre) {
        return articleRepository.findByTitreContainingIgnoreCase(titre);
    }

    @Override
    public List<Article> getRecentArticles() {
        return articleRepository.findAllByOrderByDatePublicationDesc();
    }
}