package com.microservicios.springbootservicioproductos.service;

import java.util.List;

import com.microservicios.springbootservicioproductos.entity.Producto;

public interface ProductoService {

	public List<Producto> findAll();
	public Producto findById(Long idg);
	
}
