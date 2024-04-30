package com.microservicios.springbootservicioproductos.service;

import java.util.List;

import com.microservicios.springbootserviciocommons.models.Producto;

public interface ProductoService {

	public List<Producto> findAll();
	public Producto findById(Long idg);
	public Producto save(Producto producto);
	public void deleteById(Long id);
	
}
