package com.gustavo.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavo.backend.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	boolean existsByEmail(String email);
	
}
