Feature: CRUD suit scenarios

  @crud-2 @Acceptance @Suit
  Scenario: create suit
    Given I create new project
    And I have a suit object
    When I send POST request with suit
    Then The suit should be created

  @crud-3 @Acceptance @Case
  Scenario: create case
    Given I create new project
    And I create new suit
    And I have a case object
    When I send POST request with case
    Then The case should be created