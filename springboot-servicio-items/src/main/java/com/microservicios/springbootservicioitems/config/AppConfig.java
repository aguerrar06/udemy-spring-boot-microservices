package com.microservicios.springbootservicioitems.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.thoughtworks.xstream.core.util.Pool.Factory;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {

	@Bean("clienteRest")
	@LoadBalanced
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> {
			return new Resilience4JConfigBuilder(id)
						.circuitBreakerConfig(CircuitBreakerConfig.custom()
								.slidingWindowSize(10) // Cantidad de peticiones de la ventana deslizante
								.failureRateThreshold(50) // Porcentaje de peticiones fallidas en la ventana deslizante
								.waitDurationInOpenState(Duration.ofSeconds(10L)) // Duración en segundos del circuito en estado abierto
								.permittedNumberOfCallsInHalfOpenState(5) // Numero de llamadas permitidas en el estado semi abierto
								.slowCallRateThreshold(50) // Configura el porcentaje de llamadas lentas antes que el cortocircuito se abra
								.slowCallDurationThreshold(Duration.ofSeconds(2L)) // Tiempo maximo de una llamada para que contabilice como llamada lenta
								.build())
						.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(6L)) // Segundos permitidos antes de que se lanze un error de Timeout
								.build())
						.build();
		});
	}
	
}
