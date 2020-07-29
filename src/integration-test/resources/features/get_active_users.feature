Feature: Service should provide endpoint list active users
  Check if service can list all active users

  Scenario: Unauthenticated user should not have access
    And I GET to '/v1/users/active'
    Then I should receive the status code 403

  Scenario: Active users should be listed without paging info
    Given I am authenticated
    When I GET to '/v1/users/active'
    Then I should receive the status code 200
    And The response data 'data.data' property should have 4 items
    And The response should have a 'data.lastPage' property with the value 'true'
    And The response should have a 'data.totalPages' property with the value 1
    And The response should have a 'data.totalItems' property with the value 4

  Scenario: Active users should be listed without paging info, after inactivating a user
    Given I am authenticated
    And I inactivate the user 4
    When I GET to '/v1/users/active'
    Then I should receive the status code 200
    And The response data 'data.data' property should have 3 items
    And The response should have a 'data.lastPage' property with the value 'true'
    And The response should have a 'data.totalPages' property with the value 1
    And The response should have a 'data.totalItems' property with the value 3
