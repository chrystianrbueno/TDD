package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.UsuarioBuilder;
import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.matchers.MatchersProprios;
import br.ce.wcaquino.utils.DataUtils;
import buildermaster.BuilderMaster;

public class LocacaoServiceTest {

	private LocacaoService service;
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		service = new LocacaoService();
		LocacaoDAO dao = Mockito.mock(LocacaoDAO.class); 
		service.setLocacaoDAO(dao);
	}

	@Test
	public void deveAlugarFilme() throws Exception {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(5.0).agora());

		// acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificacao
		error.checkThat(locacao.getValor(), CoreMatchers.is(5.0));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
		error.checkThat(locacao.getDataRetorno(), MatchersProprios.ehHojeComDiferencaDias(1));

	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilmeSemEstoque().agora());

		// acao
		service.alugarFilme(usuario, filmes);
	}

	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			// TODO Auto-generated catch block
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuario vazio"));
		}
	}

	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException {
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		try {
			service.alugarFilme(usuario, null);
			Assert.fail();
		} catch (LocadoraException e) {
			// TODO Auto-generated catch block
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Filme vazio"));
		}
	}

	@Test
	public void devolverNaSegunda() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

		Locacao retorno = service.alugarFilme(usuario, filmes);

		assertThat(retorno.getDataRetorno(), MatchersProprios.caiNumaSegunda());
	}

	public static void main(String[] args) {
		new BuilderMaster().gerarCodigoClasse(Locacao.class);
	}
}
