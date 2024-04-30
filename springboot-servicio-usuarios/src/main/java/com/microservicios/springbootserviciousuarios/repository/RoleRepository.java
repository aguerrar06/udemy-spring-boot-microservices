package com.microservicios.springbootserviciousuarios.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.microservicios.springbootserviciousuarioscommons.entity.Usuario;

public interface RoleRepository extends PagingAndSortingRepository<Usuario, Long> {

}
