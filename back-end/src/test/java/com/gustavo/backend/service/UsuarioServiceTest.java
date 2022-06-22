package com.gustavo.backend.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import com.gustavo.backend.exception.RegraNegocioException;
import com.gustavo.backend.model.repository.UsuarioRepository;
import com.gustavo.backend.service.impl.UsuarioServiceImpl;

@RunWith(SpringRunner.class)
@Profile("test")
public class UsuarioServiceTest {

	private UsuarioService service;
	
	@MockBean
	private UsuarioRepository repository;
	
	/*
	 * Mocks -> cria instâncias fakes onde podemos simular a chamada de métodos e
	 * 			retorno de propriedades.
	 * */
	
	//configurando testes
	@Before 
	public void setUp() {
		service = new UsuarioServiceImpl(repository);
	}
	
	@Test(expected = Test.None.class) //espera que uma exceção n seja lançada
	public void deveValidarEmail() {
		//cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//ação
		service.validarEmail("usu@gmail");
	}
	
	@Test(expected = RegraNegocioException.class) //espera que lance uma exceção
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		
		//cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//acao
		service.validarEmail("usu@gmail");
		
	}
	
}
