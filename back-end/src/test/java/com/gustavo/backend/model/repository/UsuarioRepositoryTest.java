package com.gustavo.backend.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import com.gustavo.backend.model.entity.Usuario;
/*
 * Teste de integração:
 * 
 * é o teste que precisa de recursos externos à aplicação
 * 
 * */

@SpringBootTest
@RunWith(SpringRunner.class)
@Profile("test")
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		//cenário
		Usuario usuario = Usuario.builder().nome("usu").email("usu@gmail").build();
		repository.save(usuario);
		
		//ação-execução
		boolean result = repository.existsByEmail(usuario.getEmail());

		//verificação
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		//cenário
		repository.deleteAll();
		
		//ação
		boolean result = repository.existsByEmail("usu@gmail");
		
		//verificação
		Assertions.assertThat(result).isFalse();
	}
}
