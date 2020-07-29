Feature: Service should provide endpoint to get user info by ID
  Check if service can return information from an user

  Scenario: Unauthenticated user should not have access
    When I GET to '/v1/users/1'
    Then I should receive the status code 403

  Scenario: User with ID greater than 98 cannot be accessed
    Given I am authenticated
    When I GET to '/v1/users/99'
    Then I should receive the status code 403

  Scenario: User not existing should fail
    Given I am authenticated
    When I GET to '/v1/users/10'
    Then I should receive the status code 404

  Scenario Outline: Authenticated users can find users with ID less than 99
    Given I am authenticated
    When I GET to '/v1/users/<id>'
    Then I should receive the status code 200
    And The response should have a 'data.id' property with the value <id>
    And The response should have a 'data.name' property with the value '<name>'
    And The response should have a 'data.email' property with the value '<email>'
    And The response should have a 'data.group' property with the value '<group>'

    Examples:
      | id | name   | email          | group   |
      | 1  | User 1 | user1@mail.com | Group 1 |
      | 2  | User 2 | user2@mail.com | Group 2 |
      | 3  | User 3 | user3@mail.com | Group 4 |
