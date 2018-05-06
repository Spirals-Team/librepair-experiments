
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class LoginSimulation2 extends Simulation {

	val httpProtocol = http
		.baseURL("http://localhost:8090")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36")

	val headers_0 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_1 = Map("Accept" -> "text/css,*/*;q=0.1")

	val headers_6 = Map("Origin" -> "http://localhost:8090")

	val headers_8 = Map("Accept" -> "text/event-stream")



	val scn = scenario("LoginSimulation2")
		.exec(http("request_0")
			.get("/index")
			.headers(headers_0)
			.resources(http("request_1")
			.get("/css/bootstrap.min.css")
			.headers(headers_1),
            http("request_2")
			.get("/css/tabla.css")
			.headers(headers_1),
            http("request_3")
			.get("/js/bootstrap.min.js"),
            http("request_4")
			.get("/js/jquery.min.js"),
            http("request_5")
			.get("/js/metisMenu.min.js"),
            http("request_6")
			.get("/fonts/glyphicons-halflings-regular.woff2")
			.headers(headers_6)))
		.pause(5)
		.exec(http("request_7")
			.get("/index/login?id=operator1&pass=asd")
			.headers(headers_0)
			.resources(http("request_8")
			.get("/raw")
			.headers(headers_8),
            http("request_9")
			.get("/css/bootstrap.min.css")
			.headers(headers_1),
            http("request_10")
			.get("/css/tabla.css")
			.headers(headers_1),
            http("request_11")
			.get("/js/bootstrap.min.js"),
            http("request_12")
			.get("/js/metisMenu.min.js"),
            http("request_13")
			.get("/js/jquery.min.js"),
            http("request_14")
			.get("/fonts/glyphicons-halflings-regular.woff2")
			.headers(headers_6)))
		.pause(1)
		.exec(http("request_15")
			.get("/index")
			.headers(headers_0)
			.resources(http("request_16")
			.get("/raw")
			.headers(headers_8),
            http("request_17")
			.get("/css/bootstrap.min.css")
			.headers(headers_1),
            http("request_18")
			.get("/css/tabla.css")
			.headers(headers_1),
            http("request_19")
			.get("/js/jquery.min.js"),
            http("request_20")
			.get("/js/metisMenu.min.js"),
            http("request_21")
			.get("/js/bootstrap.min.js"),
            http("request_22")
			.get("/fonts/glyphicons-halflings-regular.woff2")
			.headers(headers_6)))

	setUp(scn.inject(rampUsers(20) over (10 seconds))).protocols(httpProtocol)
}