Feature: Role change

  @Acceptance @UserRoleChangeTestEngineer
  Scenario: change user role on TEST_ENGINEER
    Given I logged in as admin
    And I have new user
    When I change user role on TEST_ENGINEER
    Then User role must be changed

  @Acceptance @UserRoleChangeTestLead
  Scenario: change user role on TEST_LEAD
    Given I logged in as admin
    And I have new user
    When I change user role on TEST_LEAD
    Then User role must be changed

  @Acceptance @UserRoleChangeGuest
  Scenario: change user role on GUEST
    Given I logged in as admin
    And I have new user
    When I change user role on GUEST
    Then User role must be changed

  @Failure @UserRoleChangeAdmin
  Scenario: change user role on ADMIN
    Given I logged in as admin
    And I have new user
    When I change user role on ADMIN
    Then User role should not be changed
