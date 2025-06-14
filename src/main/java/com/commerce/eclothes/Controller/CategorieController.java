package com.commerce.eclothes.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.commerce.eclothes.Entity.Categorie;
import com.commerce.eclothes.Repository.CategorieRepository;

@Controller
@RequestMapping("/categories")
public class CategorieController {

    @Autowired
    private CategorieRepository categorieRepository;

    // Page d'accueil - Affiche toutes les catégories
    @GetMapping("/")
    public String accueil(Model model) {
        model.addAttribute("categories", categorieRepository.findAll());
        return "categorie/liste"; // Redirige vers accueil.html
    }

    @GetMapping("/dashboard")
    public String userCategorie(Model model) {
        model.addAttribute("categories", categorieRepository.findAll());
        return "categorie/liste_dashboard"; // Redirige vers userCategorie.html
    }

    // Liste des catégories
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categorieRepository.findAll());
        return "categorie/liste";
    }

    // Formulaire d'ajout
    @GetMapping("/dashboard/ajouter")
    public String showAddForm(Model model) {
        model.addAttribute("categorie", new Categorie());
        return "categorie/form";
    }

    // Enregistrer nouvelle catégorie
    @PostMapping("/dashboard/ajouter")
    public String addCategorie(@Valid @ModelAttribute("categorie") Categorie categorie, BindingResult result) {
        if (result.hasErrors()) {
            return "categorie/form";
        }
        categorieRepository.save(categorie);
        return "redirect:/categories";
    }

    // Formulaire de modification
    @GetMapping("/dashboard/modifier/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID invalide : " + id));
        model.addAttribute("categorie", categorie);
        return "categorie/form";
    }

    // Supprimer
    @GetMapping("/dashboard/supprimer/{id}")
    public String deleteCategorie(@PathVariable Integer id) {
        categorieRepository.deleteById(id);
        return "redirect:/categories/dashboard";
    }

    // Traitement de la modification (POST)
    @PostMapping("/dashboard/modifier/{id}")
    public String updateCategorie(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("categorie") Categorie categorie,
            BindingResult result) {
        if (result.hasErrors()) {
            return "categorie/form";
        }

        // Vérifier que l'ID existe avant d'enregistrer
        Categorie existing = categorieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de catégorie invalide : " + id));

        existing.setNom(categorie.getNom()); // mise à jour des champs
        existing.setDescription(categorie.getDescription()); // mise à jour des champs
        existing.setThumbnail(categorie.getThumbnail()); // mise à jour des champs
        categorieRepository.save(existing);

        return "redirect:/categories/dashboard";
    }
}
