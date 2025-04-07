package com.example.demo.controllers;

import com.example.demo.entities.Article;
import com.example.demo.services.ArticleServiceImpl;
import com.example.demo.services.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleServiceImpl articleService;
    private final AdminServiceImpl adminService;

    @Autowired
    public ArticleController(ArticleServiceImpl articleService, AdminServiceImpl adminService) {
        this.articleService = articleService;
        this.adminService = adminService;
    }

    // Endpoint pour tous les utilisateurs pour lire les articles
    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable int id) {
        return articleService.getArticleById(id)
                .map(article -> new ResponseEntity<>(article, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Article>> searchArticles(@RequestParam String titre) {
        List<Article> articles = articleService.searchArticlesByTitle(titre);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Article>> getRecentArticles() {
        List<Article> articles = articleService.getRecentArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Endpoints pour l'admin
    @PostMapping("/admin")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        try {
            Article newArticle = adminService.createArticle(article);
            return new ResponseEntity<>(newArticle, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable int id, @RequestBody Article article) {
        try {
            Article updatedArticle = adminService.updateArticle(id, article);
            return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable int id) {
        try {
            adminService.deleteArticle(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<List<Article>> getAdminArticles() {
        List<Article> articles = adminService.getAdminArticles();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
}