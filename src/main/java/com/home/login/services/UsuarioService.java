package com.home.login.services;

import com.home.login.model.Usuario;
import com.home.login.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioService {

	private final UsuarioRepository repository;
	private final PasswordEncoder encoder;

	public Usuario save(Usuario usuario) {
		usuario.setPassword(encoder.encode(usuario.getPassword()));
		return repository.save(usuario);
	}

	public ResponseEntity<Usuario> validLogin(String login, String password) {
		Optional<Usuario> usuario = repository.findByLogin(login);

		if(usuario.isPresent()){
			if(encoder.matches(password, usuario.get().getPassword()))
				return ResponseEntity.ok(usuario.get());
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	public List<Usuario> listAll() {
		return repository.findAll();
	}
}
