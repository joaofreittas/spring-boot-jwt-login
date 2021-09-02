package com.home.login.services;

import com.home.login.data.DetalheUsuarioData;
import com.home.login.model.Usuario;
import com.home.login.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DetalheUsuarioServiceImpl implements UserDetailsService {

	private final UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = repository.findByLogin(username);

		if(!usuario.isPresent()){
			throw new UsernameNotFoundException("Usuário com o nome ["+ username + "] não encontrado.");
		}

		return new DetalheUsuarioData(usuario);
	}
}
