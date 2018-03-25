Feature: Being able to login
Scenario: Login
  Login with some username

    Given a list of agents:
      | nombre  | password |
      | pepe    | pepe12   |
      | luis    | siul     |
      | mari    | mmm      |
    When I login with nombre "luis" and password "siul"
    Then I can send incidences
