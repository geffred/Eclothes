package com.commerce.eclothes.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.commerce.eclothes.Entity.Utilisateur;
import com.commerce.eclothes.Repository.UtilisateurRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = super.loadUser(userRequest);

        String email = oauthUser.getAttribute("email");
        String nom = oauthUser.getAttribute("name");

        utilisateurRepository.findByEmail(email).orElseGet(() -> {
            Utilisateur newUser = new Utilisateur();
            newUser.setEmail(email);
            newUser.setNom(nom);
            newUser.setProvider("google");
            return utilisateurRepository.save(newUser);
        });

        return oauthUser;
    }
}
