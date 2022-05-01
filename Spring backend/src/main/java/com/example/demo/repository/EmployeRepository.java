package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Employe;

public interface EmployeRepository extends JpaRepository<Employe, Integer> {

	public Employe findByNom(String nom);
}
