package com.commerce.eclothes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.eclothes.Entity.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
}
