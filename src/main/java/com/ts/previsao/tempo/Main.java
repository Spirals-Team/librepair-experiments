package com.ts.previsao.tempo;

import static spark.Spark.port;

import com.ts.previsao.tempo.cidade.CidadeDAO;
import com.ts.previsao.tempo.home.HomeController;
import com.ts.previsao.tempo.previsao.PrevisaoDAO;
import com.ts.previsao.tempo.previsao7dias.Previsao7DiasController;

public class Main {

	public static void main(String[] args) {
		port(getHerokuAssignedPort());

		CidadeDAO cidade = new CidadeDAO();
		cidade.createTableCidade();

		PrevisaoDAO previsao = new PrevisaoDAO();
		previsao.createTablePrevisao();

		new HomeController().homeRouter();
		new Previsao7DiasController().previsao7DiasRouter();
	}

	public static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567;
	}

}
