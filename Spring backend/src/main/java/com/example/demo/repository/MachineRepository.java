package com.example.demo.repository;

import java.util.Collection;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Machine;

public interface MachineRepository extends JpaRepository<Machine, Integer> {

	Machine findById(int id);
	
    @Query("select year(m.dateAchat) as annee, count(*) as nbr from Machine m group by year(m.dateAchat) order by year(m.dateAchat)")
    Collection<?> findByMachinesYear();  
}
