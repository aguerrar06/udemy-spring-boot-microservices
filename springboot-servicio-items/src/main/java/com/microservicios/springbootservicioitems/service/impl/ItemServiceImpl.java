package com.microservicios.springbootservicioitems.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservicios.springbootservicioitems.entity.Item;
import com.microservicios.springbootservicioitems.entity.Producto;
import com.microservicios.springbootservicioitems.service.ItemService;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

	@Autowired
	private RestTemplate clienteRest;
	
	@Override
	public List<Item> findAll() {
		List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://localhost:8001/producto/listar", Producto[].class));
		return productos.stream().map(p -> new Item(1, p)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		Map<String, String> pathVariables = new HashMap<>();
		pathVariables.put("id", id.toString()); 
		Producto producto = clienteRest.getForObject("http://localhost:8001/producto/ver/{id}", Producto.class, pathVariables);
		return new Item(cantidad, producto);
	}

}
