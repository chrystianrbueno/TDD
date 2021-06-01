package br.ce.wcaquino.servicos;

import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import junit.framework.Assert;

public class CalculadoraTest {

	private Calculadora calc;
	@Test
	public void deveSomarDoisValores() {
		int a = 5;
		int b = 3;
		
		calc = new Calculadora();
		int resultado = calc.somar(a,b);
		
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void subtrairDoisValores() {
		int a = 8;
		int b = 5;
		
		calc = new Calculadora();
		int resultado = calc.subtrair(a,b);
		
		Assert.assertEquals(3, resultado);
	}

	@Test
	public void dividirDoisValores() throws NaoPodeDividirPorZeroException {
		int a = 6;
		int b = 3;
		
		calc = new Calculadora();
		int resultado = calc.dividir(a,b);
		
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		int a = 10;
		int b = 0;
		
		calc = new Calculadora();
		calc.dividir(a,b);
	}

}
