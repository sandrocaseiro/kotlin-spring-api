Feature: Service should provide endpoint to return information of the authenticated user
  Check if service can return information from the authenticated user

  Scenario: Unauthenticated user should not have access
    When I GET to '/v1/users/current'
    Then I should receive the status code 403

  Scenario Outline: Authenticated users should view it's own info
    Given I am authenticated with '<email>'
    When I GET to '/v1/users/current'
    Then I should receive the status code 200
    And The response should have a 'data.id' property with the value <id>
    And The response should have a 'data.name' property with the value '<name>'
    And The response should have a 'data.email' property with the value '<email>'

    Examples:
      | id | name   | email          |
      | 1  | User 1 | user1@mail.com |
      | 2  | User 2 | user2@mail.com |
      | 3  | User 3 | user3@mail.com |
