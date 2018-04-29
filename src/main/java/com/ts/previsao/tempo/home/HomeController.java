package com.ts.previsao.tempo.home;

import static spark.Spark.get;

public class HomeController {

	/**
	 * Aqui deve ter o conte�do inicial "home" da api
	 * com como usar, link do github, e outras informa��es
	 * @param req
	 * @param res
	 * @return
	 */
	public void homeRouter() {
		get("/", (req, res) -> new HomeModel().info());
	}
	
	
	
	
	
}
