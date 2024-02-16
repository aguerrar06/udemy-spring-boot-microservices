package com.microservicios.springbootservicioproductos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.springbootservicioproductos.entity.Producto;
import com.microservicios.springbootservicioproductos.service.ProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	@Value("${server.port}")
	private Integer port;
	
	@GetMapping("/listar")
	public List<Producto> listar() {
		return productoService.findAll();
	}
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) {
		boolean ok = false;
		Producto producto = productoService.findById(id);
		producto.setPort(port);
		
		if (!ok) {
			throw new RuntimeException("No se pudo cargar el producto");
		}
		
		return producto;
	}
	
}