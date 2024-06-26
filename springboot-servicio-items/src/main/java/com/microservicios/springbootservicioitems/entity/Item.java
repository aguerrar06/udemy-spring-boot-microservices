package com.microservicios.springbootservicioitems.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.microservicios.springbootserviciocommons.models.Producto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

	private Integer cantidad;
	private Producto producto;	
	
	public Double getTotal() {
		return producto.getPrecio() * cantidad.doubleValue();
	}
	
}
