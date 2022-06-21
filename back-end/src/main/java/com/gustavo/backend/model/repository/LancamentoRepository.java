package com.gustavo.backend.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gustavo.backend.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
