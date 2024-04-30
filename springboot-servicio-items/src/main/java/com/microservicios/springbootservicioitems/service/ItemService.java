package com.microservicios.springbootservicioitems.service;

import java.util.List;

import com.microservicios.springbootservicioitems.entity.Item;
import com.microservicios.springbootserviciocommons.models.Producto;

public interface ItemService {

	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);
	public Producto save(Producto producto);
	public Producto update(Long id, Producto producto);
	public void delete(Long id);
	
}
