package com.gustavo.backend.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavo.backend.model.entities.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
