#Feature file for the test EP-325, which steps are available in JIRA: https://mercurio.psl.com.co/jira/browse/EP-325
#Made by Jenny Marcela Zapata Pulgarín


Feature: When a request is send to the API to get topics by status, it responds
  correctly with a list of topics with the specified status.

  Scenario: A list of groups to be open, with the status equals to 0
    Given The API has a list of groups with status=0 already saved
    When I send a get request by status equals to 0
    Then I should get a successful response
    And The groups have the correct attributes for the status 0



  Scenario: A list of opened groups, with the status equals to 1
    Given The API has a list of groups with status=1 already saved
    When I send a get request by status equals to 1
    Then I should get a successful response
    And The groups have the correct attributes for the status 1



  Scenario: A list of closed groups, with the status equals to 2
    Given The API has a list of groups with status=2 already saved
    When I send a get request by status equals to 2
    Then I should get a successful response
    And The groups have the correct attributes for the status 2
