package com.commerce.eclothes.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.commerce.eclothes.Entity.Utilisateur;
import com.commerce.eclothes.Repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utilisateur enregistrerNouvelUtilisateur(Utilisateur utilisateur) {
        // Encodage du mot de passe
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

        // Vérification et ajout du rôle USER
        if (utilisateur.getRoles() == null || utilisateur.getRoles().isEmpty()) {
            utilisateur.getRoles().add("USER");
        }

        return utilisateurRepository.save(utilisateur);
    }
}