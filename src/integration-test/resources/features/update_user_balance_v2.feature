Feature: Service should provide endpoint to update users balances (v2)
  Check if service can update users balances

  Scenario: Unauthenticated user cannot update an user balance
    When I use the payload:
    """
    {
      "balance": 50.25
    }
    """
    And I PATCH to '/v2/users/1/balance'
    Then I should receive the status code 403

  Scenario: Cannot update user balance with empty values
    Given I am authenticated
    When I use the payload:
    """
    {
      "balance": ""
    }
    """
    And I PATCH to '/v2/users/1/balance'
    Then I should receive the status code 422
    And The response has an error with code 905 containing 'balance'

  Scenario: Cannot update user balance with null values
    Given I am authenticated
    When I use the payload:
    """
    {
      "balance": null
    }
    """
    And I PATCH to '/v2/users/1/balance'
    Then I should receive the status code 422
    And The response has an error with code 905 containing 'balance'

  Scenario: Cannot update user balance with negative values
    Given I am authenticated
    When I use the payload:
    """
    {
      "balance": -10.64
    }
    """
    And I PATCH to '/v2/users/1/balance'
    Then I should receive the status code 422
    And The response has an error with code 905 containing 'balance'

  Scenario: Cannot update user balance with authenticated user not from Group 1
    Given I am authenticated with 'user2@mail.com'
    When I use the payload:
    """
    {
      "balance": 50.25
    }
    """
    And I PATCH to '/v2/users/1/balance'
    Then I should receive the status code 403

  Scenario: User cannot update a user balance if API is not working
    Given I am authenticated
    And External API is not working
    When I use the payload:
    """
    {
      "balance": 50.25
    }
    """
    And I PATCH to '/v2/users/1/balance'
    Then I should receive the status code 500

  Scenario: Cannot update a user balance if he does not exists
    Given I am authenticated
    And External API is working
    When I use the payload:
    """
    {
      "balance": 50.25
    }
    """
    And I PATCH to '/v2/users/2/balance'
    Then I should receive the status code 404

  Scenario: Authenticated user from Group 1 can update a user balance
    Given I am authenticated
    And External API is working
    When I use the payload:
    """
    {
      "balance": 50.25
    }
    """
    And I PATCH to '/v2/users/1/balance'
    Then I should receive the status code 204
