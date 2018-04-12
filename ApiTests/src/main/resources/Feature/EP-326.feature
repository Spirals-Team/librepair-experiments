# Feature file of the test case made by John Bryan Yepez Herrera
# The test is based on EP-326: "https://mercurio.psl.com.co/jira/browse/EP-326"

Feature: EP-326, Verify that the api responds appropriately to a request to update a topic.
    
Scenario: The API should update topic's status from toOpen to open according to the specified Json in a patch request.
    Given the api have already topics with status 0
    When I send a patch request to change the status to 1
    Then The topic must be updated correctly
    And must be stored in the database as a topic with status 1

Scenario: The API should update topic's status from open to close according to the specified Json in a patch request.
    Given the api have already topics with status 1
    When I send a patch request to change the status to 2
    Then The topic must be updated correctly
    And must be stored in the database as a topic with status 2

Scenario: The API should update topic's status from toOpen to close according to the specified Json in a patch request.
    Given the api have already topics with status 0
    When I send a patch request to change the status to 2
    Then The topic must be updated correctly
    And must be stored in the database as a topic with status 2