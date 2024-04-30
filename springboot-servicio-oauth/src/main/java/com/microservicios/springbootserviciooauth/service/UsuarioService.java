package com.microservicios.springbootserviciooauth.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservicios.springbootserviciooauth.clients.UsuarioFeignClient;
import com.microservicios.springbootserviciousuarioscommons.entity.Usuario;

@Service
public class UsuarioService implements UserDetailsService {

	private Logger log = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioFeignClient client;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = client.findByUsername(username);
		
		if (Objects.isNull(usuario)) {
			log.error("Error en el login, no existe el usuario '" + username + "' en el sistema");
			throw new UsernameNotFoundException("Error en el login, no existe el usuario '" + username + "' en el sistema");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles()
													.stream()
													.map(role -> new SimpleGrantedAuthority(role.getNombre()))
													.peek(authority -> log.info("Role: " + authority.getAuthority()))
													.collect(Collectors.toList());
		
		log.info("Usuario autenticado: " + username);
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

}
