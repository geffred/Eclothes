package com.commerce.eclothes.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.commerce.eclothes.Entity.Categorie;
import com.commerce.eclothes.Entity.Produit;
import com.commerce.eclothes.Repository.CategorieRepository;
import com.commerce.eclothes.Repository.ProduitRepository;

import java.util.List;

@Controller
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    // Liste des produits
    @GetMapping("/")
    public String listProduits(Model model) {
        model.addAttribute("produits", produitRepository.findAll());
        return "produit/liste";
    }

    @GetMapping("/dashboard")
    public String listProduitsUser(Model model) {
        model.addAttribute("produits", produitRepository.findAll());
        model.addAttribute("categories", categorieRepository.findAll());
        return "produit/liste_dasboard";
    }

    // Formulaire d'ajout
    @GetMapping("/dashboard/ajouter")
    public String showAddForm(Model model) {
        model.addAttribute("produit", new Produit());
        model.addAttribute("categories", categorieRepository.findAll());
        return "produit/form";
    }

    // Enregistrer produit
    @PostMapping("/dashboard/ajouter")
    public String addProduit(@Valid @ModelAttribute("produit") Produit produit, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categorieRepository.findAll());

            return "produit/form";
        }
        produitRepository.save(produit);
        return "redirect:/produits/dashboard";
    }

    // Formulaire de modification
    @GetMapping("/dashboard/modifier/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID invalide : " + id));
        model.addAttribute("produit", produit);
        model.addAttribute("categories", categorieRepository.findAll());
        return "produit/form";
    }

    // Supprimer
    @GetMapping("/dashboard/supprimer/{id}")
    public String deleteProduit(@PathVariable Integer id) {
        produitRepository.deleteById(id);
        return "redirect:/produits/dashboard";
    }

    // Produits par catégorie
    @GetMapping("/categorie/{id}")
    public String produitsParCategorie(@PathVariable Integer id, Model model) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));
        List<Produit> produits = produitRepository.findByCategorieId(id);
        model.addAttribute("categorie", categorie);
        model.addAttribute("produits", produits);
        return "produit/par_categorie";
    }

    @PostMapping("/dashboard/modifier/{id}")
    public String updateProduit(@PathVariable Integer id,
            @Valid @ModelAttribute("produit") Produit produit,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categorieRepository.findAll());
            return "produit/form";
        }

        // Récupérer l'ancien produit de la base
        Produit ancienProduit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID invalide : " + id));

        // Mettre à jour les champs
        ancienProduit.setNom(produit.getNom());
        ancienProduit.setThumbnail(produit.getThumbnail());
        ancienProduit.setTaille(produit.getTaille());
        ancienProduit.setCouleur(produit.getCouleur());
        ancienProduit.setPrix(produit.getPrix());
        ancienProduit.setQuantite(produit.getQuantite());
        ancienProduit.setCategorie(produit.getCategorie());

        produitRepository.save(ancienProduit);

        return "redirect:/produits/dashboard";
    }

    @GetMapping("/detail/{id}")
    public String showProductDetail(@PathVariable Integer id, Model model) {

        Produit p = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé avec l'ID : " + id));
        List<Produit> produits = produitRepository.findByCategorieId(p.getCategorie().getId());
        model.addAttribute("produits", produits);

        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé avec l'ID : " + id));
        model.addAttribute("produit", produit);
        return "produit/produit_detail";
    }

}
