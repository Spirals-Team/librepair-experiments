Feature: Test lead assigned projects

  Scenario: Sing in as test lead
    When  I sign in
      | email              | password |
      | testLead@email.com | qwerty   |
    Then I am signed in

  Scenario: Get list of my projects
    When I try to get projects
    Then I get projects

