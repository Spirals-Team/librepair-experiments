Feature: Registration

  Scenario: registration
    When I register a new user
      | email       | name | surname | password |
      | 1@email.com | user | test    | qwerty   |
    Then User is created

  Scenario: registration test eng
    When I register a new user
      | email             | name  | surname | password |
      | testEng@email.com | user  | test    | qwerty   |
    Then User is created

  Scenario: registration test lead
    When I register a new user
      | email              | name | surname | password |
      | testLead@email.com | user | test    | qwerty   |
    Then User is created