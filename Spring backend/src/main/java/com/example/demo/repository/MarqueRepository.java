package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.example.demo.model.Marque;

@Component
public interface MarqueRepository extends JpaRepository<Marque, Long> {

	Marque findById(long id);
}
