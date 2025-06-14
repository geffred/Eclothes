package com.commerce.eclothes.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.commerce.eclothes.Entity.CustomUserDetails;
import com.commerce.eclothes.Entity.Utilisateur;
import com.commerce.eclothes.Services.UtilisateurService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "register"; // Retourne à la vue avec les erreurs
        }
        utilisateurService.enregistrerNouvelUtilisateur(utilisateur);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof OAuth2User oauthUser) {
                model.addAttribute("email", oauthUser.getAttribute("email"));
                model.addAttribute("nom", oauthUser.getAttribute("given_name"));
            } else if (principal instanceof CustomUserDetails customUserDetails) {
                model.addAttribute("nom", customUserDetails.getNom());
                model.addAttribute("email", customUserDetails.getUsername());
            }
        }
        return "dashboard";
    }

    @GetMapping("/logout-success")
    public String logoutPage() {
        return "logout-success";
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws Exception {
        // Invalider la session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Nettoyer le contexte de sécurité
        SecurityContextHolder.clearContext();

        // Vérifier si l'utilisateur était connecté via Google
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            // Rediriger vers le logout Google (double logout)
            String googleLogoutUrl = "https://accounts.google.com/Logout?continue=https://appengine.google.com/_ah/logout?continue=http://localhost:8080/logout-success";
            response.sendRedirect(googleLogoutUrl);
        } else {
            // Rediriger vers la page de succès de déconnexion
            response.sendRedirect("/logout-success");
        }
    }
}
