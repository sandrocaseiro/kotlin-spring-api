Feature: Service should provide a user report endpoint
  Check if service can generate a iuser report

  Scenario: Unauthenticated user should not have access
    And I GET to '/v1/users/report'
    Then I should receive the status code 403

    # TODO: Test all value formats
  Scenario: Authenticated users can call the report
    Given I am authenticated
    When I GET to '/v1/users/report'
    Then I should receive the status code 200
    And The response data should have 4 items
    And The response should have a 'data[0].balance' property containing 'USD'
