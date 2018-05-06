Feature: manageIncidences
  Scenario: operator manage a incidence
    Given The operator is logged on
    	And The operator is in the operator panel
    	And it has assigned incidents
    When The operator clicks "manage"
    Then The operator sees the incidence manager panel
    	And The operator can write a comment