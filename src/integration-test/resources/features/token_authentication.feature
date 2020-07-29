Feature: Service should have token authentication
  Check if the service generates a token for valid users and requests

  Scenario: Invalid user should return Bad Request
    When I use form-urlencoded
    And I use the formparams:
      | username | user99@mail.com |
      | password | 1234            |
    And I POST to '/v1/token'
    Then I should receive the status code 400
    And The response contains error code 909

  Scenario: User try to login with wrong content-type
    When I use the payload:
      """
      {
        "username": "user1@mail.com",
        "password": "1234"
      }
      """
    And I POST to '/v1/token'
    Then I should receive the status code 415

  Scenario: User with wrong credentials should return Unauthorized
    When I use form-urlencoded
    And I use the formparams:
      | username | user1@mail.com |
      | password | 4321           |
    And I POST to '/v1/token'
    Then I should receive the status code 401

  Scenario: Valid user should return OK and token information
    When I use form-urlencoded
    And I use the formparams:
      | username | user1@mail.com |
      | password | 1234           |
    And I POST to '/v1/token'
    Then I should receive the status code 200
    And The response should have a 'access_token' property
