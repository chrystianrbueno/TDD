package br.ce.wcaquino.servicos;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.UsuarioBuilder;
import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.daos.LocacaoDAOFake;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
	private LocacaoService service;

	@Parameter
	public List<Filme> filmes;

	@Parameter(value = 1)
	public Double valorLocacao;

	@Parameter(value = 2)
	public String cenario;

	@Before
	public void setup() {
		service = new LocacaoService();
		LocacaoDAO dao = new LocacaoDAOFake();
		service.setLocacaoDAO(dao);
	}

	public static Filme filme1 = FilmeBuilder.umFilme().agora();
	public static Filme filme2 = FilmeBuilder.umFilme().agora();
	public static Filme filme3 = FilmeBuilder.umFilme().agora();
	public static Filme filme4 = FilmeBuilder.umFilme().agora();
	public static Filme filme5 = FilmeBuilder.umFilme().agora();
	public static Filme filme6 = FilmeBuilder.umFilme().agora();
	public static Filme filme7 = FilmeBuilder.umFilme().agora();

	@Parameters(name = "{2}")
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object[][] { { Arrays.asList(filme1, filme2), 8.0, "2 Filmes Sem Desconto" },
				{ Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes 25%" },
				{ Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes 50%" },
				{ Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes 75%" },
				{ Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes 100%" },
				{ Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "6 Filmes 100%" } });
	}

	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();

		// acao
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificacao
		Assert.assertThat(resultado.getValor(), CoreMatchers.is(valorLocacao));

		System.out.println("!");
	}

}
