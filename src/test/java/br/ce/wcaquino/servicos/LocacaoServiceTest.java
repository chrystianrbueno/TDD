package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoServiceTest {
	
	private LocacaoService service;
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		System.out.println("Before");
		service = new LocacaoService();
	}
	
	@Test
	public void testeLocacao() throws Exception {
		//cenario
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme", 1, 5.0));
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		//verificacao
		error.checkThat(locacao.getValor(), CoreMatchers.is(5.0));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true)) ;
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception{
		//cenario
		Usuario usuario = new Usuario("Usuário 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme", 0, 5.0));

		
		//acao
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		List<Filme> filmes = Arrays.asList(new Filme("Filme", 1, 5.0));
		
			try {
				service.alugarFilme(null, filmes);
				Assert.fail();
			} catch (LocadoraException e) {
				// TODO Auto-generated catch block
				Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuario vazio"));
			}
	}
	
	@Test
	public void testLocacao_filmeVazio() throws FilmeSemEstoqueException {
		Usuario usuario = new Usuario("Usuário 1");
			try {
				service.alugarFilme(usuario, null);
				Assert.fail();
			} catch (LocadoraException e) {
				// TODO Auto-generated catch block
				Assert.assertThat(e.getMessage(), CoreMatchers.is("Filme vazio"));
			}
	}
	
}
