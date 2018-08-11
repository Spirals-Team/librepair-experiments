package com.agiles.UI.selenide;

import org.junit.Test;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;


public class UITestAhorcado {
	
	
	@Test
	public void ingresarUnaSolaLetra() {
		
		 preparePage();
		 $("#letterh").click();
		 $("#letrasUsadas").shouldHave(text("h"));
		 $(".letras-palabra").shouldHave(text("h _ _ _ "));
		 $(".errores").shouldHave(text("4"));
		 $(".ganador-section").shouldNot(exist);
		 $(".perdedor-section").shouldNot(exist);
		 $(".hangman").isImage();
		 $(".hangman").shouldHave(attribute("src","http://localhost:8080/AhorcadoWeb/images/playhangman1.png"));
	}
	
	@Test
	public void juegoGanado() {
		
		 preparePage();
		 $("#letterh").click();
		 $("#lettero").click();
		 $("#letterl").click();
		 $("#lettera").click();
		 $("#letrasUsadas").shouldNot(exist);
		 $(".letras-palabra").shouldHave(text("h o l a "));
		 $(".errores").shouldNot(exist);
		 $(".alphabets").shouldNot(exist);
		 $(".ganador-tit").shouldHave(text("Te Salvaste Perro!"));
		 $(".perdedor-section").shouldNot(exist);
		 $(".hangman").isImage();
		 $(".hangman").shouldHave(attribute("src","http://localhost:8080/AhorcadoWeb/images/playhangman1.png"));
	}
	
	@Test
	public void juegoPerdido() {
		
		 preparePage();
		 $("#letterg").click();
		 $("#letterr").click();
		 $("#lettere").click();
		 $("#lettert").click();
		 $("#letterz").click();
		 $("#letrasUsadas").shouldNot(exist);
		 $(".letras-palabra").shouldHave(text("_ _ _ _ "));
		 $(".errores").shouldNot(exist);
		 $(".alphabets").shouldNot(exist);
		 $(".ganador-section").shouldNot(exist);
		 $(".perdedor-tit").shouldHave(text("Perdiste Perro!"));
		 $(".palabraera").shouldHave(text("La palabra era: hola"));
		 $(".hangman").isImage();
		 $(".hangman").shouldHave(attribute("src","http://localhost:8080/AhorcadoWeb/images/playhangman6.png"));
	}
	
	@Test
	public void juegoGanadoConCincoLetras(){
		preparePage();
		 $("#letterh").click();
		 $("#letterr").click();
		 $("#letterl").click();
		 $("#lettera").click();
		 $("#lettero").click();
		 $("#letrasUsadas").shouldNot(exist);
		 $(".letras-palabra").shouldHave(text("h o l a "));
		 $(".errores").shouldNot(exist);
		 $(".alphabets").shouldNot(exist);
		 $(".ganador-tit").shouldHave(text("Te Salvaste Perro!"));
		 $(".perdedor-section").shouldNot(exist);
		 $(".hangman").isImage();
		 $(".hangman").shouldHave(attribute("src","http://localhost:8080/AhorcadoWeb/images/playhangman2.png"));
	
	}
	
	
	
	public void preparePage(){
//		System.setProperty("selenide.browser", "chrome");
		open("/AhorcadoWeb/index.jsp");
	}
}


