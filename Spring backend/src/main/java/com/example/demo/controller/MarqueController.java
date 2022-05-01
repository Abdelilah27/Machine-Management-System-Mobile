package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Marque;
import com.example.demo.repository.MarqueRepository;

@RestController
@RequestMapping("marques")
public class MarqueController {
	
	@Autowired
	MarqueRepository marqueRepository;

	@PostMapping("/save")
	public void save(@RequestBody Marque marque){
		marqueRepository.save(marque);
	}
	
	@GetMapping("/all")
	public List<Marque> load(){
		return marqueRepository.findAll();
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable long id){
		marqueRepository.delete(marqueRepository.findById(id));
	}
	
	@GetMapping("/count")
	public Map<String, Integer> count (){
		
		Map<String, Integer> map = new HashMap<>();
		for(Marque m : marqueRepository.findAll()){
			map.put(m.getLibelle(), m.getMachines().size());
		}
		return map;
	}
	
}
