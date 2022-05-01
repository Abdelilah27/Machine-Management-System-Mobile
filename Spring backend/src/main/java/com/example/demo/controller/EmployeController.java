package com.example.demo.controller;

import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employe;
import com.example.demo.repository.EmployeRepository;

@RestController
@RequestMapping("employes")
public class EmployeController {
	@Autowired
	private EmployeRepository employeRepository;
	
	@PostMapping("/save")
	public void save(@RequestBody Employe employe){
		employeRepository.save(employe);
	}

	@GetMapping("/all")
	public List<Employe> findAll(){
		return employeRepository.findAll();
	}
}
