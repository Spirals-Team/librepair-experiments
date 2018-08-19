package com.agiles.UI.Cucumber;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

import cucumber.api.java.en.Then;

public class LoseTheGameStepDefinitions {
	
	@Then("^a title saying \"([^\"]*)\" should appear$")
	public void a_title_saying_should_appear(String loseTit) throws Throwable {
		$(".perdedor-tit").shouldHave(text(loseTit));
	}

	@Then("^the hanged man should appear in the image$")
	public void the_hanged_man_should_appear_in_the_image() throws Throwable {
		$(".hangman").shouldHave(attribute("src","http://localhost:8080/AhorcadoWeb/images/playhangman6.png"));
	}

	@Then("^a message saying the correct word should appear$")
	public void a_message_saying_the_correct_word_should_appear() throws Throwable {
		$(".palabraera").shouldHave(text("La palabra era: hola"));
	}
}
