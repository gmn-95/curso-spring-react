package com.gustavo.backend.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.gustavo.backend.exception.ErroAutenticacao;
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
		Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado para o e-mail informado");
		}

		if(!usuario.get().getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida");
		}
		
		return usuario.get();
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
