package com.gustavo.backend.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gustavo.backend.exception.RegraNegocioException;
import com.gustavo.backend.model.entity.Usuario;
import com.gustavo.backend.model.repository.UsuarioRepository;
import com.gustavo.backend.service.UsuarioService;

@Service //tornando a classe em um bean gerenciado
public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioRepository repository;
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional //abre uma transação no banco, e depois commita a alteração
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		if(repository.existsByEmail(email)) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail");
		}
	}
}
