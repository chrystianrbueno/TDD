package br.ce.wcaquino.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

public class DataDiferencasDiasMatcher extends TypeSafeMatcher<Date> {

	private Integer qtdDias;
	public DataDiferencasDiasMatcher(Integer qtdDias) {
		this.qtdDias = qtdDias;
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(qtdDias));
	}

	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}

}
