package com.microservicios.springbootservicioitems.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.microservicios.springbootservicioitems.clients.ProductoClienteRest;
import com.microservicios.springbootservicioitems.entity.Item;
import com.microservicios.springbootserviciocommons.models.Producto;
import com.microservicios.springbootservicioitems.service.ItemService;

@Service("serviceFeign")
@Primary
public class ItemServiceFeign implements ItemService {

	@Autowired
	private ProductoClienteRest clienteFeign;

	@Override
	public List<Item> findAll() {
		return clienteFeign.listar().stream().map(p -> new Item(1, p)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		return new Item(cantidad, clienteFeign.detalle(id));
	}

	@Override
	public Producto save(Producto producto) {
		return clienteFeign.crear(producto);
	}

	@Override
	public Producto update(Long id, Producto producto) {
		return clienteFeign.actualizar(id, producto);
	}

	@Override
	public void delete(Long id) {
		clienteFeign.eliminar(id);
	}
	
}
