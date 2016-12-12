Feature: Tests

  Scenario: Test status
    When I call status endpoint
    Then response code is 200
    And content is "OK"

  Scenario: Test insert person
    Given I have a person
    When I insert the person
    Then response code is 204
    And the person exists in the database

  Scenario: Test get person
    Given there is an existing person
    When I get the person
    Then response code is 200
    And the person details are correct

  Scenario: Test get person returns 404
    When I get the person
    Then response code is 404