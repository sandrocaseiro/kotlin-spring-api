Feature: Service should provide endpoint to get users from a Group
  Check if service can return information from all users in a Group

  Scenario: Unauthenticated user should not have access
    When I GET to '/v1/users/group/1'
    Then I should receive the status code 403

# Update after creating procedures for H2
#  Scenario: Search with a not existing group should return no users
#    Given I am authenticated
#    When I GET to '/v1/users/group/10'
#    Then I should receive the status code 300
#    And The response data should be an empty list
