Feature: Service should provide endpoint to create new users
  Check if service can create create new users

  Scenario: Unauthenticated user can create a user
    When With content-type: 'application/json'
    * With payload:
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
    * The client calls user create endpoint
    Then The client receives status code of 201
    * The response has the created user info
    * The user is created in the database as active

  Scenario: Authenticated user can create a user
    Given I am authenticated
    When With content-type: 'application/json'
    * With payload:
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
    * The client calls user create endpoint
    Then The client receives status code of 201
    * The response has the created user info
    * The user is created in the database as active

  Scenario: Cannot create user with same e-mail
    When With content-type: 'application/json'
    * With payload:
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
    * The client calls user create endpoint
    Then The client receives status code of 422
    * The response returns error code 904

  Scenario: Cannot create user with invalid data
    When With content-type: 'application/json'
    * With payload:
      """
      {
        "name": "User 5",
        "email": "user1mail.com",
        "cpf": "20454349072",
        "password": "1234",
        "groupId": 1,
        "roles": []
      }
      """
    * The client calls user create endpoint
    Then The client receives status code of 422
    * The response returns error code 905
    * The response has 3 errors
    * The response contains error for the cpf field
    * The response contains error for the email field
    * The response contains error for the roles field
