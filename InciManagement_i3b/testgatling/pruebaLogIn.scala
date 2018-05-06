
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class prueba01 extends Simulation {

	val httpProtocol = http
		.baseURL("http://localhost:8080")
		.inferHtmlResources()

	val headers_0 = Map(
		"Upgrade-Insecure-Requests" -> "1",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")

	val headers_1 = Map("User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")

	val headers_10 = Map(
		"Content-Type" -> "application/x-www-form-urlencoded;charset=UTF-8",
		"accept" -> "*/*",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-US,en;q=0.9",
		"content-length" -> "1405",
		"content-type" -> "application/x-www-form-urlencoded;charset=UTF-8",
		"origin" -> "https://notifications.google.com",
		"referer" -> "https://notifications.google.com/",
		"user-agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36",
		"x-client-data" -> "CJS2yQEIorbJAQjEtskBCKmdygEIqKPKARiSo8oB")

	val headers_12 = Map(
		"accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"accept-encoding" -> "gzip, deflate, br",
		"accept-language" -> "en-US,en;q=0.9",
		"cookie" -> "CONSENT=YES+ES.es+20150628-20-0; OGPC=19005035-1:; SID=8AV1bYgosXHEJXY57xzMNPwJKjLRANFyluR9lkjwLim59Sjzn4bbrOCLeDPcdN-uNmPQoA.; HSID=A7vG57vNgTJWvTDEp; SSID=AcJ6bCcm1h8HvEqlC; APISID=ANRwhKdfJp6EOq7x/A9mEe96da-_41c9Pt; SAPISID=74MH8WF_tiIM-8RI/A7SUlZTs3GuD-v-Vh; NID=128=NV48cSNgRNCs6i5hIu_0SOXkPXt2aYt_flULWRuUszZH4krcXc7snnaEfBvsIjhq6vwvFpP7yq4WicxgDKoRSwMhA3d094tm2beBok2CkSXK8UZzSCn2jkyPST7G83-uQA3__ZGi8obYNw37lLj8F4wz11cYWE-ZFzCm2fllFdbyBr8j_ux9Z6QzNiJ8l1SH0knEQQOCzqxnMPH1hQL0j1d53c325iu_u4-ewxd8g0b7Q4vORVhpxdxx7r9Ifs0jXISRIBNy2crY38IpnkMAPNgNJRZcI3a608995I71ewssLhyWfS2k; 1P_JAR=2018-4-27-9",
		"referer" -> "https://www.google.es/_/chrome/newtab?ie=UTF-8",
		"user-agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36",
		"x-client-data" -> "CJS2yQEIorbJAQjEtskBCKmdygEIqKPKARiSo8oB")

	val headers_17 = Map(
		"Accept" -> "text/css,*/*;q=0.1",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Connection" -> "keep-alive",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")

	val headers_18 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Connection" -> "keep-alive",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")

	val headers_19 = Map(
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
		"Accept-Encoding" -> "gzip, deflate, br",
		"Accept-Language" -> "en-US,en;q=0.9",
		"Cache-Control" -> "max-age=0",
		"Connection" -> "keep-alive",
		"Origin" -> "http://localhost:8080",
		"Upgrade-Insecure-Requests" -> "1",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")

	val headers_20 = Map(
		"Origin" -> "http://localhost:8080",
		"User-Agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")

    val uri1 = "https://play.google.com/log"
    val uri2 = "https://notifications.google.com"
    val uri3 = "https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"
    val uri5 = "https://www.google.es/gen_204"
    val uri6 = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7"
    val uri7 = "https://apis.google.com"

	val scn = scenario("prueba01")
		.exec(http("request_0")
			.get(uri2 + "/u/0/widget?sourceid=243&hl=es&origin=https%3A%2F%2Fwww.google.es&uc=1&usegapi=1&jsh=m%3B%2F_%2Fscs%2Fabc-static%2F_%2Fjs%2Fk%3Dgapi.gapi.en.fXLlmSb25lg.O%2Fm%3D__features__%2Frt%3Dj%2Fd%3D1%2Frs%3DAHpOoo87O_nfyKCryZ5rvwwhVwktxKhiRA")
			.headers(headers_0)
			.resources(http("request_1")
			.get(uri2 + "/_/scs/social-static/_/js/k=boq.NotificationsOgbUi.es.iEIwiRZk9ik.O/m=_b,_tp/am=AAEABAIAAQ/rt=j/d=1/excm=home,_b,_tp/ed=1/rs=AGLTcCMvtcyTOQrlqmD-x61-oLxeGOInyA")
			.headers(headers_1),
            http("request_2")
			.get(uri2 + "/_/scs/social-static/_/js/k=boq.NotificationsOgbUi.es.iEIwiRZk9ik.O/ck=boq.NotificationsOgbUi.8kahone293xk.L.W.O/m=sy42,sy3p,sy45,sy47,sy48,sy43,sy3z,sy49,sy46,wmwg8b/am=AAEABAIAAQ/rt=j/d=0/excm=home,_b,_tp/ed=1/rs=AGLTcCMQ8xz5Fh5P1WrAFOUdrHGR8dNm3g")
			.headers(headers_1),
            http("request_3")
			.get(uri2 + "/_/scs/social-static/_/js/k=boq.NotificationsOgbUi.es.iEIwiRZk9ik.O/ck=boq.NotificationsOgbUi.8kahone293xk.L.W.O/m=sy3v,sy67,OJUrvb/am=AAEABAIAAQ/rt=j/d=0/excm=home,_b,_tp/ed=1/rs=AGLTcCMQ8xz5Fh5P1WrAFOUdrHGR8dNm3g")
			.headers(headers_1),
            http("request_4")
			.get(uri2 + "/_/scs/social-static/_/js/k=boq.NotificationsOgbUi.es.iEIwiRZk9ik.O/ck=boq.NotificationsOgbUi.8kahone293xk.L.W.O/m=sy65,sy66,XAzchc,sy3m,sy3n,wI7Sfc,ws9Tlc,sy5f,IZT63,sy5r,sy5s,sy3o,sy5t,sy5u,e5qFLc,GkRiKb,sy5l,sy3l,sy5m,sy5k,sy5j,sy5n,sy5o,sy5p,sy5q,syad,WO9ee,sy6r,sy6s,mI3LFb,sy5w,azoZEe,sy8e,sy8f,sy8g,sy8h,sy8i,O6y8ed,sy3s,sy4d,sy4e,sy4f,sy3k,sy4g,sy4i,sy3w,sy6a,sy4h,sy69,dodICd,sy6b,sy4y,sy6f,sy54,sy6c,sy68,Y9atKf,sy6g,sy5e,sy6d,sy6h,sy6j,sy6k,sy6i,PrPYRd,iWP1Yb,sy6u,sy3t,sy6w,sy6y,sy6x,sy6v,sy6t,sy6z,mdR7q,sy7y,XVMNvd,sy7z,sy82,sy8b,sy8d,sy8c,L1AAkb,NtGY3c,sy5y,sy7x,F3ZVPc,lwddkf,sy58,syqp,syqq,o7nxec,sylf,syqs,LUjVKc,sy7g,sy7e,sy7c,sy7d,sy7f,sy7h,sy7i,sy3r,sy7j,sy7l,sy4q,sy7k,sy7m,sy7o,sy87,sy86,sy7n,sy7p,sy88,sy92,sy95,sy96,CyO4we,syqr,RQErad,hc6Ubd,sy73,MI6k7c,sy70,sy71,sy72,kjKdXe,sy4j,sy9w,sy4k,sy9z,sya0,sya1,em3,sya2,em5,em4,SdcwHb,aW3pY,EFQ78c,G4es5d,XbSRYb,MJAy1b,o02Jie,SpsfSb,hKSk3e,sy50,sy63,sy7r,sy9x,sy64,sy9k,sy9v,MdUzUe,sy76,sy77,sy4c,sy7s,sy7b,sy9p,sy4l,sy4m,syx,syy,sy4b,sy4n,sy4o,sy4r,sy4s,sy7v,syr2,syr3,sy4p,sy7q,syqt,syr4,bvGsFe,sy3x,sy3u,sy4x,zemu5,sy5z,sy60,sy61,sy62,sy5x,zbML3c,Uas9Hd,LbJKvc/am=AAEABAIAAQ/rt=j/d=0/excm=home,_b,_tp/ed=1/rs=AGLTcCMQ8xz5Fh5P1WrAFOUdrHGR8dNm3g")
			.headers(headers_1),
            http("request_5")
			.get(uri7 + "/js/api.js")
			.headers(headers_1),
            http("request_6")
			.get(uri2 + "/_/scs/social-static/_/js/k=boq.NotificationsOgbUi.es.iEIwiRZk9ik.O/ck=boq.NotificationsOgbUi.8kahone293xk.L.W.O/m=NTMZac,sy7w,SKR2De/am=AAEABAIAAQ/rt=j/d=0/excm=home,_b,_tp/ed=1/rs=AGLTcCMQ8xz5Fh5P1WrAFOUdrHGR8dNm3g")
			.headers(headers_1),
            http("request_7")
			.get(uri2 + "/_/scs/social-static/_/js/k=boq.NotificationsOgbUi.es.iEIwiRZk9ik.O/ck=boq.NotificationsOgbUi.8kahone293xk.L.W.O/m=sy55,sy51,sy53,sy56,sy52,qfNSff,sy57,sy40,sy59,sy5a,sy4z,em1,sy5b,ZxDaqc,w9hDv/am=AAEABAIAAQ/rt=j/d=0/excm=home,_b,_tp/ed=1/rs=AGLTcCMQ8xz5Fh5P1WrAFOUdrHGR8dNm3g")
			.headers(headers_1),
            http("request_8")
			.get(uri7 + "/_/scs/abc-static/_/js/k=gapi.gapi.en.fXLlmSb25lg.O/m=gapi_iframes_simple/rt=j/sv=1/d=1/ed=1/rs=AHpOoo87O_nfyKCryZ5rvwwhVwktxKhiRA/cb=gapi.loaded_0")
			.headers(headers_1),
            http("request_9")
			.get(uri2 + "/_/scs/social-static/_/js/k=boq.NotificationsOgbUi.es.iEIwiRZk9ik.O/ck=boq.NotificationsOgbUi.8kahone293xk.L.W.O/m=sy3y,_latency,FCpbqb,WhJNk/am=AAEABAIAAQ/rt=j/d=0/excm=home,_b,_tp/ed=1/rs=AGLTcCMQ8xz5Fh5P1WrAFOUdrHGR8dNm3g")
			.headers(headers_1),
            http("request_10")
			.post(uri1 + "?format=json")
			.headers(headers_10)
			.formParam("""[[1,null,null,null,null,null,null,null,["en-US","chrome","65.0.3325.181"],null,[null,"10.0",3,null,"es",2,"boq_notificationsuiserver_20180423.04_p0"]],241,[["1524823167415",null,[],null,null,null,null,"[1524823167158,[[\"2917163396991171123\",null,[[1489,1]]],[\"2397168675742140944\",null,[[1490,1]]],[\"16147638372540442232\",null,[[1495,1]]],[\"14719340685975485085\",null,[[1608,1]]],[\"17077408715954654437\",null,[[1914,1]]],[\"5790177495296899286\",null,[[0,1]]],[\"16829267986558572790\",null,[[940,1]]],[\"16339156775003354937\",null,[[337,1]]],[\"749851692583976763\",null,[[119,1]]],[\"15419336178855610526\",null,[[1284,1]]],[\"17276521865292187132\",null,[[4,1]]],[\"8257051839445688306\",null,[[1913,1]]],[\"7792735449360349632\",null,[[1915,1]]],[\"7094487270460551484\",null,[[9,1]]],[\"12563104964214410683\",null,[[448,1]]],[\"15605813632677093659\",null,[[22,1]]],[\"17914751415692637656\",null,[[9,1]]],[\"6342145065879578001\",null,[[9,1]]],[\"13596961294000664596\",null,[[448,1]]],[\"2107494750385856652\",null,[[22,1]]],[\"1309831198388189068\",null,[[9,1]]],[\"4950535922500196698\",null,[[2089,1]]],[\"1757184925777806825\",null,[[10,1]]],[\"3079121564595244695\",null,[[10,1]]]],null,null,\"[1,\\\"jasHAe_PL\\\"]\"]",null,null,null,880389307,null,null,null,[null,[],null,"[[],[],[1709118],[]]"],null,null,null,[],1]],"1524823167415",[],null,null,null,null,null,null,null,null,0]""", "")))
		.pause(8)
		.exec(http("request_11")
			.get(uri5 + "?atyp=i&ct=nfkbx&cad=&ei=1vfhWpH0JOTt6QS5t5eoBA&zx=1524823176337")
			.headers(headers_1)
			.resources(http("request_12")
			.get(uri5 + "?atyp=i&ct=nfkbx&cad=&ei=1vfhWpH0JOTt6QS5t5eoBA&zx=1524823176337")
			.headers(headers_12)))
		.pause(1)
		.exec(http("request_13")
			.get("/")
			.headers(headers_0)
			.resources(http("request_14")
			.get(uri3 + "")
			.headers(headers_1),
            http("request_15")
			.get(uri6 + "/js/bootstrap.min.js")
			.headers(headers_1),
            http("request_16")
			.get(uri6 + "/css/bootstrap.min.css")
			.headers(headers_1),
            http("request_17")
			.get("/css/custom.css")
			.headers(headers_17),
            http("request_18")
			.get("/img/student-48.png")
			.headers(headers_18)))
		.pause(9)
		.exec(http("request_19")
			.post("/logIn")
			.headers(headers_19)
			.formParam("login", "11111111A")
			.formParam("password", "123456")
			.formParam("kind", "1")
			.resources(http("request_20")
			.get(uri6 + "/fonts/glyphicons-halflings-regular.woff2")
			.headers(headers_20)))

	setUp(scn.inject(atOnceUsers(10))).protocols(httpProtocol)
}