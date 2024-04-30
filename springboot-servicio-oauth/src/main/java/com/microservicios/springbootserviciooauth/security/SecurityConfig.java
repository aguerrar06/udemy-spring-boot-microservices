package com.microservicios.springbootserviciooauth.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService usuarioService;

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().authenticated()
			)
			// El inicio de sesion del formulario maneja la redireccion a la pagina de inicio de sesion 
			// desde la cadena de filtro del servidor de autorizacion
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.httpBasic(Customizer.withDefaults());

		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(usuarioService);
		return provider;
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		//return usuarioService;
		UserDetails user = User.builder()
				.username("pepe")
				.password("{noop}12345")
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository() {
		RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("frontendapp") // Nombre del cliente para registrarlo
				.clientSecret("{noop}12345") // Contraseña del cliente para generar el token (Va en las cabeceras)
				.scope(OidcScopes.OPENID) // Agregamos rol que permite iniciar sesion desde la aplicacion frontend
				.scope(OidcScopes.PROFILE) // Agregamos este scope para permitir acceso basico a informaciones del perfil
				.scope("read") // Agregamos rol de usuario normal
				.scope("write") // Agregamos rol de usuario administrador
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC) // Se debe iniciar sesion con el client ID y el secret del front
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // Forma de generar el token
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN) // Permite ctualizar token antes de que caduque el token actual
				.redirectUri("http://127.0.0.1:8080/login/oauth2/code/frontendapp") // URL que debe coincidir con el nombre de la app frontend
				.redirectUri("http://127.0.0.1:8080/authorized") // Endpoint para poder recuperar el token de autorizacion
				.postLogoutRedirectUri("http://127.0.0.1:8080/logout") // Ruta de redireccion despues de hacer logout
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
				.build();
				
		return new InMemoryRegisteredClientRepository(client);
	}
	
	@Bean 
	public JWKSource<SecurityContext> jwkSource() {
		// Con este método creamos un RSAKey que se registra en el backend y permite firmar los token
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey rsaKey = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}
	
	@Bean 
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}
	
	@Bean
	private static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private static KeyPair generateRsaKey() { 
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		}
		catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

}