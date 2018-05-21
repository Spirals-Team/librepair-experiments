Feature: Generate and download cucumber feature file


  Scenario: Successfully download file
    Given I have a project
      | name         | description         |
      | project_name | project_description |
    And I have a suit
      | name      | description      | priority |
      | suit_name | suit_description | 1        |
    And I have a case
      | name      | description      | comment      | priority |
      | case name | case description | case comment | 1        |
    And I have a step
      | description      | comment      | status  | type |
      | step description | step comment | SKIPPED | THEN |
    When I download file
    Then The file should be returned

  Scenario: Unsuccessfully download file
    Given I have a project
      | name         | description         |
      | project_name | project_description |
    And I have a suit
      | name      | description      | priority |
      | suit_name | suit_description | 1        |
    And I have a case that doesn't exist in data base
      | id |
      | -1 |
    When I download file
    Then The case shouldn't be founded
