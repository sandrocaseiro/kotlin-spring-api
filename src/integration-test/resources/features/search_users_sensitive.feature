Feature: Service should provide endpoint to search users using sensitive data
  Check if service can search users using a text filter

  Scenario: Unauthenticated user cannot search
    When I use the payload:
      """
      {
        "searchContent": "35636471009"
      }
      """
    And I POST to '/v1/users/search'
    Then I should receive the status code 403

  Scenario Outline: Cannot search users without a search term
    Given I am authenticated
    When I use the payload:
    """
    {
      "searchContent": <term>
    }
    """
    And I POST to '/v1/users/search'
    Then I should receive the status code 422
    And The response has an error with code 905 containing 'searchContent'

    Examples:
      | term |
      | ""   |
      | null |

  Scenario: Search with a not existing term should return no users
    Given I am authenticated
    When I use the payload:
      """
      {
        "searchContent": "35636471001"
      }
      """
    When I POST to '/v1/users/search'
    Then I should receive the status code 200
    And The response data should be an empty list

  Scenario: Search with the term '35636471009' should return only one user
    Given I am authenticated
    When I use the payload:
      """
      {
        "searchContent": "35636471009"
      }
      """
    When I POST to '/v1/users/search'
    Then I should receive the status code 200
    And The response data should have 1 items
    And The response should have a 'data[0].id' property with the value 1
