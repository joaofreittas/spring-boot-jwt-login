package com.home.login.controller;

import com.home.login.dto.LoginDTO;
import com.home.login.model.Usuario;
import com.home.login.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioController {

	private final UsuarioService service;

	@PostMapping
	public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
		return ResponseEntity.ok(service.save(usuario));
	}

	@GetMapping("/valid-login")
	public ResponseEntity<Usuario> validLogin(@RequestBody LoginDTO dto){
		return service.validLogin(dto.getLogin(), dto.getPassword());
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}

}
