package com.agiles.UI.Cucumber;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import cucumber.api.java.en.Then;


public class EnterALetterStepDefinitions {
	

	
	@Then("^the letter \"([^\"]*)\" should appear below the image$")
	public void the_letter_should_appear_below_the_image(String letter) throws Throwable {
		$(".letras-palabra").shouldHave(text(letter+" _ _ _ "));
	}

	@Then("^the letter \"([^\"]*)\" should appear in the entered letters$")
	public void the_letter_should_appear_in_the_entered_letters(String letter) throws Throwable {
		$("#letrasUsadas").shouldHave(text(letter));
	}

	@Then("^the remaining errors should be (\\d+)$")
	public void the_remaining_errors_should_be(int nume) throws Throwable {
		$(".errores").shouldHave(text(String.valueOf(nume)));;
	}

	@Then("^the letter \"([^\"]*)\" should not appear below the image$")
	public void the_letter_should_not_appear_below_the_image(String letter) throws Throwable {
		$(".letras-palabra").shouldHave(text("_ _ _ _ "));
	}
	
	@Then("^the hanged man should not appear in the image$")
	public void the_hanged_man_should_not_appear_in_the_image() throws Throwable {
		$(".hangman").shouldHave(attribute("src","http://localhost:8080/AhorcadoWeb/images/playhangman1.png"));
	}

	@Then("^the head of the hanged man should appear in the image$")
	public void the_head_of_the_hanged_man_should_appear_in_the_image() throws Throwable {
		$(".hangman").shouldHave(attribute("src","http://localhost:8080/AhorcadoWeb/images/playhangman2.png"));
	}
}
