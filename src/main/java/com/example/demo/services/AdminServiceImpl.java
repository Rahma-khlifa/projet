package com.example.demo.services;

import com.example.demo.entities.Admin;
import com.example.demo.entities.Article;
import com.example.demo.repositories.AdminRepository;
import com.example.demo.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements IAdminService {

    private final AdminRepository adminRepository;
    private final ArticleRepository articleRepository;
    private static final int ADMIN_ID = 1; // ID fixe pour l'unique admin

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, ArticleRepository articleRepository) {
        this.adminRepository = adminRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public Admin getAdmin() {
        return adminRepository.findById((long) ADMIN_ID)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    // Méthodes pour la gestion des articles
    @Override
    public Article createArticle(Article article) {
        Admin admin = getAdmin();
        article.setAdmin(admin);
        article.setDatePublication(new Date());
        return articleRepository.save(article);
    }

    @Override
    public Article updateArticle(int articleId, Article articleDetails) {
        return articleRepository.findById(articleId).map(article -> {
            article.setTitre(articleDetails.getTitre());
            article.setContenu(articleDetails.getContenu());
            return articleRepository.save(article);
        }).orElseThrow(() -> new RuntimeException("Article not found with id " + articleId));
    }

    @Override
    public List<Article> getAdminArticles() {
        return articleRepository.findByAdminId(ADMIN_ID);
    }

    @Override
    public void deleteArticle(int articleId) {
        articleRepository.deleteById(articleId);
    }
}