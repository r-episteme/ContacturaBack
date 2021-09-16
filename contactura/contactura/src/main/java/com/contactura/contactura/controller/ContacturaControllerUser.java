package com.contactura.contactura.controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contactura.contactura.model.ContacturaUser;
import com.contactura.contactura.repository.ContacturaUserRepository;

@CrossOrigin()
@RestController
@RequestMapping({"/user"})
public class ContacturaControllerUser {
	
	@Autowired
	private ContacturaUserRepository repository;
	
	@RequestMapping("/login")
	@GetMapping
//	public String login(HttpServletRequest request) {
	public List<String> login(HttpServletRequest request) throws UnsupportedEncodingException {
// String t0 = request.;
// System.out.println(t0);
	
//	String t1 = request.getHeader("Authorization");
//	System.out.println(t1);
//	String t2 = request.getHeader("Authorization").substring("Basic".length());
//	System.out.println(t2);
	
// Estudo de criptografia
//	byte[] bytes = "frase qualquer de teste".getBytes("UTF-8");
//	String usernameEncoded = Base64.getEncoder().encodeToString(bytes);
//	byte[] decoded = Base64.getDecoder().decode(usernameEncoded);
//	System.out.println(newString(decoded,StandardCharsets.UTF_8));
	
		
		String authorization = request.getHeader("Authorization").substring("Basic".length()).trim();
		byte[]baseCred = Base64.getDecoder().decode(authorization);
		String credentialsParsed = new String(baseCred, StandardCharsets.UTF_8);
		String[] values = credentialsParsed.split(":", 2);
		ContacturaUser user = repository.findByUsername(values[0]);
	
	
		String token = request.getHeader("Authorization").substring("Basic".length()).trim();
//      ArrayList<String> lista = new ArrayList<String>();
//      lista.add(Boolean.toString(user.isAdmin()));
//      list.add(token);

//return lista;
			return Arrays.asList(Boolean.toString(user.getAdmin()), token);
	}
	
//List All
	@GetMapping
	public List<?> findAll() {
		return repository.findAll();
	}
//Find By Id 
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable long id){
		return repository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	
//Create
	@PostMapping
	@PreAuthorize("hasRole('Admin')")
	public ContacturaUser create(@RequestBody ContacturaUser user){
		user.setPassword(encryptPassword(user.getPassword()));
		return repository.save(user);
	}
	
//Update
	@PutMapping(value = "{id}")
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<?> update(@PathVariable long id, 
			@RequestBody ContacturaUser user) {
		return repository.findById(id)
				.map(record -> {
					record.setName(user.getName());
					record.setPassword(encryptPassword(user.getPassword()));
					record.setUsername(user.getUsername());
					record.setAdmin(user.getAdmin());
					ContacturaUser update = repository.save(record);
					return ResponseEntity.ok().body(update);
				}).orElse(ResponseEntity.notFound().build());
	}

//Delete
	@DeleteMapping(path = {"/{id}"})
	@PreAuthorize("hasRole('Admin')")
	public ResponseEntity<?> delete(@PathVariable long id){
		return repository.findById(id)
				.map(record -> {
					repository.deleteById(id);
					return ResponseEntity.ok().build();				
				}).orElse(ResponseEntity.notFound().build());
	}
	
	private String encryptPassword(String password){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String passwordCrypt = passwordEncoder.encode(password);
		
		return passwordCrypt;
	}
}


	