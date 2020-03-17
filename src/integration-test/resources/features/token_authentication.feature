Feature: Service should have token authentication
  Check if the service generates a token for valid users and requests

  Scenario: Invalid user should return Unauthorized
    When With content-type: 'application/x-www-form-urlencoded'
    * The client calls /v1/token endpoint with username: user99@mail.com and password: 1234
    Then The client receives status code of 401

  Scenario: Valid user should return OK and token information
    When With content-type: 'application/x-www-form-urlencoded'
    * The client calls /v1/token endpoint with username: user1@mail.com and password: 1234
    Then The client receives status code of 200
    * Response returned bearer token

  Scenario: User try to login with wrong content-type
    When With content-type: 'application/json'
    * With payload:
      """
      {
        "username": "user1@mail.com",
        "password": "1234"
      }
      """
    * The client calls /v1/token endpoint
    Then The client receives status code of 415
