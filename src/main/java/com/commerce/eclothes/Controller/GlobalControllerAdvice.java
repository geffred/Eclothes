package com.commerce.eclothes.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.commerce.eclothes.Entity.CustomUserDetails;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addUserToModel(Model model, Authentication authentication) {
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
    }
}
