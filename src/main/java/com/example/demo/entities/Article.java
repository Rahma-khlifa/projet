package com.example.demo.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "articles")
public class Article {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(columnDefinition = "TEXT")
    private String contenu;
    
    @Column(name = "titre")
    private String titre;
    
    @Column(name = "date_publication")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePublication;
    
    @PrePersist
    protected void onCreate() {
        this.datePublication = new Date();
    }
    
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
    
    // Constructeurs
    public Article() {
    }
    
    public Article(String titre, String contenu, Date datePublication) {
        this.titre = titre;
        this.contenu = contenu;
        this.datePublication = datePublication;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getContenu() {
        return contenu;
    }
    
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
    
    public String getTitre() {
        return titre;
    }
    
    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public Date getDatePublication() {
        return datePublication;
    }
    
    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }
    
    public Admin getAdmin() {
        return admin;
    }
    
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", datePublication=" + datePublication +
                '}';
    }
}