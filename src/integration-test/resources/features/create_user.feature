Feature: Service should provide endpoint to create new users
  Check if service can create new users

  Scenario: Cannot create user with null values
    When I use the payload:
      """
      {
        "name": null,
        "email": null,
        "cpf": null,
        "password": null,
        "groupId": null,
        "roles": null
      }
      """
    And I POST to '/v1/users'
    Then I should receive the status code 422
    And The response has 1 errors with code 905 containing 'name'
    And The response has 1 errors with code 905 containing 'email'
    And The response has 2 errors with code 905 containing 'cpf'
    And The response has 1 errors with code 905 containing 'password'
    And The response has 1 errors with code 905 containing 'groupId'
    And The response has 1 errors with code 905 containing 'roles'

  Scenario: Cannot create user with empty values
    When I use the payload:
      """
      {
        "name": "",
        "email": "",
        "cpf": "",
        "password": "",
        "groupId": "",
        "roles": []
      }
      """
    And I POST to '/v1/users'
    Then I should receive the status code 422
    And The response has 1 errors with code 905 containing 'name'
    And The response has 1 errors with code 905 containing 'email'
    And The response has 2 errors with code 905 containing 'cpf'
    And The response has 1 errors with code 905 containing 'password'
    And The response has 1 errors with code 905 containing 'groupId'
    And The response has 1 errors with code 905 containing 'roles'

  Scenario Outline: Cannot create user with invalid e-mail
    When I use the payload:
      """
      {
        "name": "User 5",
        "email": "<email>",
        "cpf": "20454349076",
        "password": "1234",
        "groupId": 1,
        "roles": [1,2]
      }
      """
    And I POST to '/v1/users'
    Then I should receive the status code 422
    And The response has 1 errors with code 905 containing 'email'

    Examples:
      | email         |
      | user1mail.com |
      | @mail.com     |
      | user1@.com    |

  Scenario Outline: Cannot create user with invalid cpf
    When I use the payload:
      """
      {
        "name": "User 5",
        "email": "user5@mail.com",
        "cpf": "<cpf>",
        "password": "1234",
        "groupId": 1,
        "roles": [1,2]
      }
      """
    And I POST to '/v1/users'
    Then I should receive the status code 422
    And The response has an error with code 905 containing 'cpf'

    Examples:
      | cpf            |
      | 922.574.650-40 |
      | 9225746504     |
      | 922574650401   |
      | 92257465041    |

  Scenario: Cannot create user with same e-mail
    When I use the payload:
      """
      {
        "name": "User 5",
        "email": "user1@mail.com",
        "cpf": "20454349076",
        "password": "1234",
        "groupId": 1,
        "roles": [1,2]
      }
      """
    And I POST to '/v1/users'
    Then I should receive the status code 400
    And The response contains error code 904

  Scenario: Unauthenticated user can create a user
    When I use the payload:
      """
      {
        "name": "User 5",
        "email": "user5@mail.com",
        "cpf": "20454349076",
        "password": "1234",
        "groupId": 1,
        "roles": [1,2]
      }
      """
    And I POST to '/v1/users'
    Then I should receive the status code 201
    And The response should have the created user info
    And The created user should exist in the database as active

  Scenario: Authenticated user can create a user
    Given I am authenticated
    When I use the payload:
    """
    {
      "name": "User 5",
      "email": "user5@mail.com",
      "cpf": "20454349076",
      "password": "1234",
      "groupId": 1,
      "roles": [1,2]
    }
    """
    And I POST to '/v1/users'
    Then I should receive the status code 201
    And The response should have the created user info
    And The created user should exist in the database as active
