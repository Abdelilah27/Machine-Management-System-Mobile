package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Produit;
import com.example.demo.repository.ProduitRepository;

@RestController
@RequestMapping("produits")

public class ProduitController {

	@Autowired
	private ProduitRepository produitJpaRepository;

	@GetMapping("/all")
	public List<Produit> findAll() {
		return produitJpaRepository.findAll();
	}

	@GetMapping(value = "/{name}")
	public Produit findByCode(@PathVariable final String nom) {
		return produitJpaRepository.findByNom(nom);
	}

	@PostMapping(value = "/save")
	public void save(@RequestBody final Produit produit) {
		produitJpaRepository.save(produit);
	}

	@DeleteMapping(value = "/delete/{id}")
	public void delete(@PathVariable(required = true) String id) {
		System.out.println("id = "+id);
		Produit produit = produitJpaRepository.findById(Long.parseLong(id));
		produitJpaRepository.delete(produit);
	}
	
	@GetMapping(value = "/count")
	public long countProduit() {
		return produitJpaRepository.count();
	}

}
