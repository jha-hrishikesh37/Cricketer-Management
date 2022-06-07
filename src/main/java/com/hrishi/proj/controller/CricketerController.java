package com.hrishi.proj.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hrishi.proj.dao.CricketerRepository;
import com.hrishi.proj.model.Cricketer;

@RestController
@RequestMapping(path="/api")
public class CricketerController {
	@Autowired
	CricketerRepository cricketerRepository;

	@GetMapping("/cricketers")
	public ResponseEntity<List<Cricketer>> getAllCricketers() {
		try {
			List<Cricketer> cricketers = new ArrayList<Cricketer>();
			cricketerRepository.findAll().forEach(cricketers::add); // will keep adding all crickets in crickets list created just above
			return new ResponseEntity<>(cricketers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/cricketers/{id}")
	public ResponseEntity<Cricketer> getCricketerById(@PathVariable("id") int id) {
		Optional<Cricketer> cricketerData = cricketerRepository.findById(id);
		
		if(cricketerData.isPresent()) {
			return new ResponseEntity<>(cricketerData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
//	
//	@GetMapping("/cricketers/name/{name}")
//	public ResponseEntity<Cricketer> getCricketerByName(@PathVariable("name") String name) {
//		Optional<Cricketer> cricketerData = cricketerRepository.findByName(name);
//		
//		if(cricketerData.isPresent()) {
//			return new ResponseEntity<>(cricketerData.get(), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
	
	@GetMapping("/cricketers/country/{country}")
	public ResponseEntity<List<Cricketer>> getCricketerByCountry(@PathVariable("country") String country) {
		Optional<List<Cricketer>> cricketerData = cricketerRepository.findByCountry(country);
		
		if(cricketerData.isPresent()) {
			return new ResponseEntity<>(cricketerData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/cricketers")
	public ResponseEntity<Cricketer> addCricketer(@RequestBody Cricketer cricketer) {
		try {
			Cricketer _cricketer = cricketerRepository.save(cricketer);
			return new ResponseEntity<>(_cricketer, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/cricketers/{id}")
	public ResponseEntity<Cricketer> updateCricketer(@PathVariable int id, @RequestBody Cricketer cricketer) {
		Optional<Cricketer> cricketerData = cricketerRepository.findById(id);
		
		if(cricketerData.isPresent()) {
			Cricketer _cricketer = cricketerData.get();
			_cricketer.setId(cricketer.getId());
			_cricketer.setName(cricketer.getName());
			_cricketer.setCountry(cricketer.getCountry());
			_cricketer.setRole(cricketer.getRole());
			return new ResponseEntity<>(cricketerRepository.save(cricketer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/cricketers/{id}")
	public ResponseEntity<HttpStatus> deleteCricketer(@PathVariable int id) {
		try {
			cricketerRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
