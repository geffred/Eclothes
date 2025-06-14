package com.commerce.eclothes.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Le nom est requis")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;

    @Column(nullable = false)
    @Positive(message = "Le prix doit être positif")
    private float prix;

    @Enumerated(EnumType.STRING)
    private Taille taille;

    @Column(length = 30)
    @NotBlank(message = "La couleur est requise")
    private String couleur;

    @Column
    @NotBlank(message = "l'image est requise")
    private String thumbnail;

    @Column(nullable = false)
    @Min(value = 0, message = "La quantité ne peut pas être négative")
    private Integer quantite;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    public Produit() {
    }

    public Produit(String nom, float prix, Taille taille, String couleur, String thumbnail, Integer quantite,
            Categorie categorie) {
        this.nom = nom;
        this.prix = prix;
        this.taille = taille;
        this.couleur = couleur;
        this.thumbnail = thumbnail;
        this.quantite = quantite;
        this.categorie = categorie;
    }

    // Getters & Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Taille getTaille() {
        return taille;
    }

    public void setTaille(Taille taille) {
        this.taille = taille;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
