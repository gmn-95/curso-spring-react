package com.gustavo.backend.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import com.gustavo.backend.model.entity.Usuario;
/*
 * Teste de integração:
 * 
 * é o teste que precisa de recursos externos à aplicação
 * 
 * */

@RunWith(SpringRunner.class)
@Profile("test")
@DataJpaTest/*cria uma instancia do db na memoria e depois encerra, apenas no período do test.
			* Dá rollback no final do test, ou seja, não salva as alterações
			* */
@AutoConfigureTestDatabase(replace = Replace.NONE)//usado para não desconfigurar o profile 'test'
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository repository;
	
	/*
	 * EntityManager configurado apenas para testes
	 * */
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		//cenário
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		//ação-execução
		boolean result = repository.existsByEmail(usuario.getEmail());

		//verificação
		Assertions.assertThat(result).isTrue();
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		
		//ação
		boolean result = repository.existsByEmail("usu@gmail");
		
		//verificação
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		
		//cenário
		Usuario usuario = criarUsuario();
		
		//ação
		Usuario usuarioSalvo = repository.save(usuario);
		
		//verificação
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}
	
	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		//cenário
		Usuario usuario = criarUsuario();
		entityManager.persist(usuario);
		
		//ação
		Optional<Usuario> result = repository.findByEmail("usu@gmail");
		
		//verificação
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	
	@Test
	public void deveRetornarVazioAoBuscarUmUsuarioPorEmailQuandoNaoExisteNaBase() {
		
		//ação
		Optional<Usuario> result = repository.findByEmail("usu@gmail");
		
		//verificação
		Assertions.assertThat(result.isPresent()).isFalse();
	}
	
	
	private static Usuario criarUsuario() {
		return Usuario
					.builder()
					.nome("usu")
					.email("usu@gmail")
					.senha("123")
					.build();
	}
}
