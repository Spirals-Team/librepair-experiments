Feature: CRUD project scenarios

  @crud-1 @Acceptance @Project
  Scenario Outline: create project
    Given I have a <request> object
    When I send POST request with <request>
    Then The <request> should be created

    Examples:
      | request |
      | project |
      | suit    |
      | case    |





