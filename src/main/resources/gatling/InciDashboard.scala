package es.asw

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class InciDashboard extends Simulation {

	val httpProtocol = http
		.baseURL("http://localhost:8090")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")



	val scn = scenario("InciDashboard")
		.exec(http("request_0")
			.post("/login")
			.headers(headers_0)
			.formParam("username", "Pablo")
			.formParam("password", "123456")
			.formParam("${_csrf.parameterName}", "${_csrf.token}"))
		.pause(4)
		.exec(http("request_1")
			.get("/campos")
			.headers(headers_0))
		.pause(5)
		.exec(http("request_2")
			.get("/campos/modificar/1")
			.headers(headers_0))
		.pause(24)
		.exec(http("request_3")
			.get("/campos")
			.headers(headers_0))

	setUp(scn.inject(rampUsers(40) over (5 seconds))).protocols(httpProtocol)
}