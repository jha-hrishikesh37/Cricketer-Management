package com.hrishi.proj.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hrishi.proj.model.Cricketer;

@Repository
public interface CricketerRepository extends JpaRepository<Cricketer, Integer> {
	Optional<Cricketer> findByName(String name);
	
	Optional<List<Cricketer>> findByCountry(String country);
}
