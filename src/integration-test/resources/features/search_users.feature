Feature: Service should provide endpoint to search users
  Check if service can search users using a text filter

  Scenario: Unauthenticated user cannot search
    When I use the queryparams:
      | $search | user |
    And I GET to '/v1/users'
    Then I should receive the status code 403

  Scenario: Search with a not existing term should return no users
    Given I am authenticated
    When I use the queryparams:
      | $search | $$$$ |
    And I GET to '/v1/users'
    Then I should receive the status code 200
    And The response data should be an empty list

  Scenario: Search without a term should return all users
    Given I am authenticated
    When I GET to '/v1/users'
    Then I should receive the status code 200
    And The response data should have 4 items

  Scenario: Search with the term 'user' should return all users
    Given I am authenticated
    When I use the queryparams:
      | $search | user |
    And I GET to '/v1/users'
    Then I should receive the status code 200
    And The response data should have 4 items
    And The response data name property should all contains 'user'

  Scenario: Search with the term 'user 1' should return only one user
    Given I am authenticated
    When I use the queryparams:
      | $search | user 1 |
    And I GET to '/v1/users'
    Then I should receive the status code 200
    And The response data should have 1 items
    And The response data name property should all contains 'user 1'
