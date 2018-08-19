#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Win the Game
	Several ways to win the game

 @PerfectGameTest
  Scenario: the user enters all the letters correctly
    Given an open browser with AhorcadoWeb/index.jsp
    When I click the letter "h"
    And I click the letter "l"
    And I click the letter "o"
    And I click the letter "a"
    Then a message saying "Te Salvaste!" should appear
    And the keyboard should disappear
    And the remaining errors should disappear
    And the entered letters should disappear

  @NotPerfectGameTest
  Scenario: the user wins with two wrong letters
    Given an open browser with AhorcadoWeb/index.jsp
    When I click the letter "h"
    And I click the letter "l"
    And I click the letter "x"
    And I click the letter "o"
    And I click the letter "v"
    And I click the letter "a"
    Then a message saying "Te Salvaste!" should appear
    And the keyboard should disappear
    And the remaining errors should disappear
    And the entered letters should disappear

 
