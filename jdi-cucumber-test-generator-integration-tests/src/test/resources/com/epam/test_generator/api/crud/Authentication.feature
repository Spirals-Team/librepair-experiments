Feature: Authentication

  Scenario: Sing in as user
    When I sign in
      | email           | password |
      | 1@email.com | qwerty   |
    Then I am signed in

  Scenario: Sign in as admin
    When  I sign in
      | email          | password |
      | admin@mail.com | admin    |
    Then I am signed in

  Scenario: Sing in as test eng
    When  I sign in
      | email             | password |
      | testEng@email.com | qwerty   |
    Then I am signed in

  Scenario: Sing in as test lead
    When  I sign in
      | email              | password |
      | testLead@email.com | qwerty   |
    Then I am signed in

