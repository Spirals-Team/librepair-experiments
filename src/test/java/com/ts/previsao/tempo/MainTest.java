package com.ts.previsao.tempo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MainTest {

	@Test
	public void testa_se_define_port_padrao_caso_nao_definido() {
		assertEquals(4567, Main.getHerokuAssignedPort());
	}

	@Test(timeout = 1000)
	public void testa_se_main_nao_lanca_excecao() throws InterruptedException {
		Main.main(null);
	}

}
