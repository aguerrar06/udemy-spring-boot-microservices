package com.microservicios.springbootservicioproductos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicios.springbootservicioproductos.entity.Producto;
import com.microservicios.springbootservicioproductos.repository.ProductoRepository;
import com.microservicios.springbootservicioproductos.service.ProductoService;

@Service
public class ProductoServiceimpl implements ProductoService {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return productoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		return productoRepository.findById(id).orElse(null);
	}

	
	
}
