package com.gustavo.backend.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gustavo.backend.model.entity.Lancamento;
import com.gustavo.backend.model.enums.StatusLancamento;
import com.gustavo.backend.model.repository.LancamentoRepository;
import com.gustavo.backend.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService{

	private LancamentoRepository repository;
	
	public LancamentoServiceImpl(LancamentoRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		return repository.save(lancamento);
	}

	@Override
	public Lancamento atualizar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento) {
		// TODO Auto-generated method stub
		
	}

}
