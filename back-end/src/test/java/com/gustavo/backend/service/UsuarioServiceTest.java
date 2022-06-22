package com.gustavo.backend.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import com.gustavo.backend.exception.ErroAutenticacao;
import com.gustavo.backend.exception.RegraNegocioException;
import com.gustavo.backend.model.entity.Usuario;
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
	
	@Test(expected = Test.None.class)
	public void deveAutenticarUmUsuarioComSucesso() {
		
		//cenário
		String email = "usu@gmail";
		String senha = "123";
		
		//criamos um usuário cadastrado no banco fictício
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
		
		//Simulamos o método findByEmail, dizendo que o retorno dele é o usuário fictício
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		
		//ação
		Usuario result = service.autenticar(email, senha);
		
		//verificação
		Assertions.assertThat(result).isNotNull();
	}
	
	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		
		//cenário
		String senha = "123";
		Usuario usuario = Usuario.builder().email("usu@email").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		//ação
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("usu@email", "321"));
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida");
	}
	
	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
		
		//cenário
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		//ação
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("usu@email", "123"));
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuário não encontrado para o e-mail informado");
		
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
