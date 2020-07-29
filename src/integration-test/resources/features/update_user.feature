Feature: Service should provide endpoint to update users
  Check if service can update users

  Scenario: Unauthenticated user cannot update an user
    When I use the payload:
      """
      {
        "name": "User 21",
        "cpf": "36111617052",
        "password": "12345",
        "groupId": 2,
        "roles": [1,4]
      }
      """
    And I PUT to '/v1/users/2'
    Then I should receive the status code 403

  Scenario: Cannot update user with empty values
    Given I am authenticated
    When I use the payload:
      """
      {
        "name": "",
        "cpf": "",
        "password": "",
        "groupId": "",
        "roles": []
      }
      """
    And I PUT to '/v1/users/2'
    Then I should receive the status code 422
    And The response has 1 errors with code 905 containing 'name'
    And The response has 2 errors with code 905 containing 'cpf'
    And The response has 1 errors with code 905 containing 'password'
    And The response has 1 errors with code 905 containing 'groupId'
    And The response has 1 errors with code 905 containing 'roles'

  Scenario Outline: Cannot update user with invalid cpf
    Given I am authenticated
    When I use the payload:
      """
      {
        "name": "User 21",
        "cpf": "<cpf>",
        "password": "12345",
        "groupId": 4,
        "roles": [1,4]
      }
      """
    And I PUT to '/v1/users/2'
    Then I should receive the status code 422
    And The response has an error with code 905 containing 'cpf'

    Examples:
      | cpf            |
      | 922.574.650-40 |
      | 9225746504     |
      | 922574650401   |
      | 92257465041    |

  Scenario: Authenticated user can update a user
    Given I am authenticated
    When I use the payload:
    """
    {
        "name": "User 21",
        "cpf": "36111617052",
        "password": "12345",
        "groupId": 2,
        "roles": [1,4]
    }
    """
    And I PUT to '/v1/users/2'
    Then I should receive the status code 204
    And The user 2 should have his data updated in the database
