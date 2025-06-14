package com.commerce.eclothes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.eclothes.Entity.Produit;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    List<Produit> findByCategorieId(Integer categorieId);
}
