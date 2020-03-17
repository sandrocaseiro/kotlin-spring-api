Feature: Service should provide endpoint to search user by Id
    Check if service can search allowed users by theirs Id's

    Scenario: Unauthenticated user cannot access endpoint
      When With content-type: 'application/json'
      * The client calls get by id endpoint using id: 1
      Then The client receives status code of 403

    Scenario: Authenticated user can get user information
      Given I am authenticated
      When With content-type: 'application/json'
      * The client calls get by id endpoint using id: 1
      Then The client receives status code of 200
      * The returned user has the following data:
        | id | name   | email          |
        | 1  | User 1 | user1@mail.com |

    Scenario: Authenticated user cannot get user 4 information
      Given I am authenticated
      When With content-type: 'application/json'
      * The client calls get by id endpoint using id: 4
      Then The client receives status code of 403
