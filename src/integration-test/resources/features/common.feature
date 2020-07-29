Feature: Common API test scenarios
  Check if service provide common endpoints and can handle different types of errors

  Scenario: Get API Info
    When I GET to '/_monitor/info'
    Then I should receive the status code 200
    And The response should have a 'app.name' property with the value 'api-template'

  Scenario: Call invalid endpoint
    Given I am authenticated
    When I GET to '/v1/tests'
    Then I should receive the status code 404
    And The response contains error code 404

  Scenario: Invalid HTTP Method
    Given I am authenticated
    When I POST to '/v1/users/1'
    Then I should receive the status code 405
    And The response contains error code 405

  Scenario: Unsupported media type
    Given I am authenticated
    When I use the Content-Type 'image/gif'
    And I POST to '/v1/users'
    Then I should receive the status code 415
    And The response contains error code 415

  Scenario: Not accepted media type
    Given I am authenticated
    When I use the header 'Accept' with the value 'image/gif'
    And I GET to '/v1/users/1'
    Then I should receive the status code 406
    And The response contains error code 406

  Scenario: Default response language should be english
    Given I am authenticated
    When I GET to '/v1/users/1'
    Then The response has an error with code 200 containing 'Success'

  Scenario Outline: Response language should respect the language header
    Given I am authenticated
    When I use the header 'Accept-Language' with the value '<language>'
    And I GET to '/v1/users/1'
    Then The response has an error with code 200 containing '<result>'

    Examples:
      | language | result  |
      | en-US    | Success |
      | pt-BR    | Sucesso |

  Scenario: Invalid language header should default to English
    Given I am authenticated
    When I use the header 'Accept-Language' with the value 'es-ES'
    And I GET to '/v1/users/1'
    Then The response has an error with code 200 containing 'Success'
