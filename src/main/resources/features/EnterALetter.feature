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
@tag
Feature: Enter a Letter
  Check if a letter is entered correctly

  @ClickCorrectLetter
  Scenario: the user clicks the letter h
    Given an open browser with AhorcadoWeb/index.jsp
    When I click the letter "h"
    Then the letter "h" should appear below the image
    And the letter "h" should appear in the entered letters
    And the remaining errors should be 4
    And the hanged man should not appear in the image
    
    
  @ClickWrongLetter
  Scenario: the user clicks the letter k
    Given an open browser with AhorcadoWeb/index.jsp
    When I click the letter "k"
    Then the letter "k" should not appear below the image
    And the letter "k" should appear in the entered letters
    And the remaining errors should be 3
    And the head of the hanged man should appear in the image
    


