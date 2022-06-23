package com.gustavo.backend.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
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

	/*Spy -> é similar ao mock, porém ele chama os métodos originais, 
	 * a n ser que eu diga como será o comportamento do método
	 * 
	 */
	@SpyBean
	private UsuarioServiceImpl service;
	
	@MockBean
	private UsuarioRepository repository;
	
	/*
	 * Mocks -> cria instâncias fakes onde podemos simular a chamada de métodos e
	 * 			retorno de propriedades.
	 * */
	
	@Test(expected = Test.None.class) //espera que uma exceção n seja lançada
	public void deveSalvarUmUsuario() {
		/*	@INÍCIO DO CENÁRIO	*/
		//Não faça nada ao tentar validar qualquer email
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder().id(1L).nome("Gustavo").email("teste@email").senha("123").build();
		
		//Quando salvar, retorne o usuário
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		
		/*	@INÍCIO DA AÇÃO	*/
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
		
		/*	@INÍCIO DA VERIFICAÇÃO	*/
		//Verifique que os dados são iguais
		Assertions.assertThat(usuarioSalvo).isNotNull();
		Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1L);
		Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("teste@email");
		Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("123");
	}
	
	@Test(expected = RegraNegocioException.class) //espera que lance uma exceção
	public void naoDeveSalvarUmUsuarioComUmEmailJaCadastrado() {
		/*	@INÍCIO DO CENÁRIO	*/
		String email = "teste@email";
		Usuario usuario = Usuario.builder().email(email).build();
		
		//lance uma exceção ao validar email, pois o email já existe
		Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);
		
		/*	@INÍCIO DA AÇÃO	*/
		service.salvarUsuario(usuario);
		
		/*	@INÍCIO DA VERIFICAÇÃO	*/
		//não deve chamar o método salvar usuario do repository, pois já existe um usuário com o email informado
		Mockito.verify(repository, Mockito.never()).save(usuario);
	}
	
	@Test(expected = Test.None.class) //espera que uma exceção n seja lançada
	public void deveAutenticarUmUsuarioComSucesso() {
		
		/*	@INÍCIO DO CENÁRIO */
		String email = "usu@gmail";
		String senha = "123";
		
		//criamos um usuário cadastrado no banco fictício
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
		
		//Simulamos o método findByEmail, dizendo que o retorno dele é o usuário fictício
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		/* @INÍCIO DA AÇÃO	*/
		//tenta autenticar o login
		Usuario result = service.autenticar(email, senha);
		
		/* @INÍCIO DA VERIFICAÇÃO	*/
		//espera-se que o resultado não seja nulo
		Assertions.assertThat(result).isNotNull();
	}
	
	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		
		/*	@INÍCIO DO CENÁRIO	*/
		String senha = "123";
		Usuario usuario = Usuario.builder().email("usu@email").senha(senha).build();
		
		//Encontre um usuário com qualquer email passado
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		/*	@INÍCIO DA AÇÃO	*/
		//Capture uma exceção ao tentar logar com a senha errada
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("usu@email", "321"));
		//Verifique se a exceção e a mensagem encontrada é a mesma esperada,
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida");
	}
	
	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
		
		/*	@INÍCIO DO CENÁRIO*/
		//Retorne vazio, ao tentar encontrar um usuário por qualquer email
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		/*	@INÍCIO DA AÇÃO	*/
		//Capture uma exceção ao tentar logar com o email errado
		Throwable exception = Assertions.catchThrowable(() -> service.autenticar("usu@email", "123"));
		//Verifique se a exceção e a mensagem encontrada é a mesma esperada,
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuário não encontrado para o e-mail informado");
		
	}
	
	@Test(expected = Test.None.class) //espera que uma exceção n seja lançada
	public void deveValidarEmail() {
		/*	@INÍCIO DO CENÁRIO	*/
		//Quando for verificar se um email já existe na base, retorne false
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		/*	@INÍCIO DA AÇÃO	*/
		service.validarEmail("usu@gmail");
	}
	
	@Test(expected = RegraNegocioException.class) //espera que lance uma exceção
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		
		/*	@INÍCIO DO CENÁRIO	*/
		//Quando for verificar se um email já existe na base, retorne true
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		/* @INÍCIO DA AÇÃO	*/
		//Vai lançar uma exceção, pois o email já está cadastrado
		service.validarEmail("usu@gmail");
		
	}
	
}
