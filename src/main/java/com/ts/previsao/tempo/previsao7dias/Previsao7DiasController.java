package com.ts.previsao.tempo.previsao7dias;

import static spark.Spark.get;

public class Previsao7DiasController {

	public void previsao7DiasRouter() {
		get("/uf/:uf/cidade/:cidade", (req, res) -> {
			res.type("application/json");
			return new Previsao7DiasModel().getPrevisao(req.params("uf"), req.params("cidade"));
		});
	}
}
