Feature: Authentication

  Scenario: Sing in as user
    Given I enter User data
      | email           | password |
      | 1@email.com | qwerty   |
    When I sign in
    Then I am signed in

  Scenario: Sign in as admin
    Given I enter User data
      | email          | password |
      | admin@mail.com | admin    |
    When  I sign in
    Then I am signed in

  Scenario: Sing in as test eng
    Given I enter User data
      | email             | password |
      | testEng@email.com | qwerty   |
    When  I sign in
    Then I am signed in

  Scenario: Sing in as test lead
    Given I enter User data
      | email              | password |
      | testLead@email.com | qwerty   |
    When  I sign in
    Then I am signed in

