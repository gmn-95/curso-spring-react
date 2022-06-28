package com.gustavo.backend.services.impls;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavo.backend.exceptions.ErroAutenticacao;
import com.gustavo.backend.exceptions.RegraNegocioException;
import com.gustavo.backend.model.entities.Usuario;
import com.gustavo.backend.model.repositories.UsuarioRepository;
import com.gustavo.backend.services.UsuarioService;

@Service //tornando a classe em um bean gerenciado
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
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
