Feature: Service should provide endpoint to get user info by ID (v2)
  Check if service can return information from an user

  Scenario: Unauthenticated user should not have access
    When I GET to '/v2/users/1'
    Then I should receive the status code 403

  Scenario: User with ID greater than 98 cannot be accessed
    Given I am authenticated
    When I GET to '/v2/users/99'
    Then I should receive the status code 403

  Scenario: Find user with the API not working should fail
    Given I am authenticated
    And External API is not working
    When I GET to '/v2/users/1'
    Then I should receive the status code 500

  Scenario: User not existing should fail
    Given I am authenticated
    And External API is working
    When I GET to '/v2/users/10'
    Then I should receive the status code 404

  Scenario: Authenticated users can find users with ID less than 99
    Given I am authenticated
    And External API is working
    When I GET to '/v2/users/1'
    Then I should receive the status code 200
    And The response should have a 'data.id' property with the value 1
    And The response should have a 'data.name' property with the value 'User 1'
    And The response should have a 'data.email' property with the value 'user1@mail.com'
    And The response should not have a 'data.group' property
