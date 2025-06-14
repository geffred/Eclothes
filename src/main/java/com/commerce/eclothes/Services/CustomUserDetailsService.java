package com.commerce.eclothes.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.commerce.eclothes.Entity.CustomUserDetails;
import com.commerce.eclothes.Entity.Utilisateur;
import com.commerce.eclothes.Repository.UtilisateurRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private UtilisateurRepository utilisateurRepository;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "Aucun utilisateur trouvé avec l'email: " + email));

                List<GrantedAuthority> authorities = utilisateur.getRoles().stream()
                                .map(role -> {
                                        // Assure que le rôle commence par ROLE_ si ce n'est pas déjà le cas
                                        String roleName = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                                        return new SimpleGrantedAuthority(roleName);
                                })
                                .collect(Collectors.toList());

                return new CustomUserDetails(
                                utilisateur.getEmail(),
                                utilisateur.getMotDePasse(),
                                authorities,
                                utilisateur.getNom());
        }
}