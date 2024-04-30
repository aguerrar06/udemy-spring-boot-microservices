package com.microservicios.springbootservicioproductos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microservicios.springbootserviciocommons.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
