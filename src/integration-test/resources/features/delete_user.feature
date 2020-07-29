Feature: Service should provide endpoint to delete users
  Check if service can delete users

  Scenario: Unauthenticated user cannot delete an user
    When I DELETE to '/v1/users/1'
    Then I should receive the status code 403

  Scenario: Cannot delete an user that does not exists
    Given I am authenticated
    When I DELETE to '/v1/users/99'
    Then I should receive the status code 404

  Scenario: User cannot delete himself
    Given I am authenticated with 'user1@mail.com'
    When I DELETE to '/v1/users/1'
    Then I should receive the status code 403

  Scenario: User can delete an user different than himself
    Given I am authenticated with 'user1@mail.com'
    When I DELETE to '/v1/users/2'
    Then I should receive the status code 204
    And The user 2 should be inactive
