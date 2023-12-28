package com.microservicios.springbootservicioitems.service;

import java.util.List;

import com.microservicios.springbootservicioitems.entity.Item;

public interface ItemService {

	public List<Item> findAll();
	public Item findById(Long id, Integer cantidad);
	
}
