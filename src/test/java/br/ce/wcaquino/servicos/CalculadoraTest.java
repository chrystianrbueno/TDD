package br.ce.wcaquino.servicos;

import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import junit.framework.Assert;

public class CalculadoraTest {

	private Calculadora calc = new Calculadora();
	@Test
	public void deveSomarDoisValores() {
		int a = 5;
		int b = 3;
		
		int resultado = calc.somar(a,b);
		
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void subtrairDoisValores() {
		int a = 8;
		int b = 5;
		
		int resultado = calc.subtrair(a,b);
		
		Assert.assertEquals(3, resultado);
	}

	@Test
	public void dividirDoisValores() throws NaoPodeDividirPorZeroException {
		int a = 6;
		int b = 3;
		
		int resultado = calc.dividir(a,b);
		
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		int a = 10;
		int b = 0;
		
		calc.dividir(a,b);
	}
	
	@Test
	public void deveDividir() {
		String a = "6";
		String b = "3";
		
		int resultado = calc.divide(a, b);
		
		Assert.assertEquals(2, resultado);
	}
}
