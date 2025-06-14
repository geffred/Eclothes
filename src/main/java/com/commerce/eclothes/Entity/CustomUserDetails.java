package com.commerce.eclothes.Entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private final String nom;

    public CustomUserDetails(String username, String password,
            Collection<? extends GrantedAuthority> authorities,
            String nom) {
        super(username, password, authorities);
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}