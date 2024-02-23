package com.microservicios.springbootservicioitems.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservicios.springbootservicioitems.entity.Producto;

@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

	@GetMapping("/productos/listar")
	public List<Producto> listar();
	
	@GetMapping("/productos/ver/{id}")
	public Producto detalle(@PathVariable Long id);
	
}
