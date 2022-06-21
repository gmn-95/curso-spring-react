package com.gustavo.backend.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import com.gustavo.backend.exception.RegraNegocioException;
import com.gustavo.backend.model.entity.Usuario;
import com.gustavo.backend.model.repository.UsuarioRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@Profile("test")
public class UsuarioServiceTest {

	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioRepository repository;
	
	@Test(expected = Test.None.class) //espera que uma exceção n seja lançada
	public void deveValidarEmail() {
		
		//cenário
		repository.deleteAll();
		
		//ação
		service.validarEmail("usu@gmail");
	}
	
	@Test(expected = RegraNegocioException.class) //espera que lance uma exceção
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		
		//cenário
		Usuario usuario = Usuario.builder().nome("usu").email("usu@gmail").build();
		repository.save(usuario);
		
		//acao
		service.validarEmail("usu@gmail");
		
	}
	
}
