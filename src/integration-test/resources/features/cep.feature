Feature: CEP search scenarios
  Check if service can manage to search information regarding a CEP

  Scenario: Unauthenticated user should not have access to search CEP
    When I GET to '/v1/cep/03310000'
    Then I should receive the status code 403
    And The response contains error code 403

  Scenario: Search CEP without the external API
    Given I am authenticated
    But CEP API is not working
    When I GET to '/v1/cep/03310000'
    Then I should receive the status code 500
    And The response contains error code 902

  Scenario: Search invalid CEP
    Given I am authenticated
    And CEP API is working
    When I GET to '/v1/cep/1234'
    Then I should receive the status code 500
    And The response contains error code 902

  Scenario: Search not existent CEP
    Given I am authenticated
    And CEP API is working
    When I GET to '/v1/cep/99999999'
    Then I should receive the status code 404
    And The response contains error code 903


  Scenario: Search for existent CEP
    Given I am authenticated
    And CEP API is working
    When I GET to '/v1/cep/03310000'
    Then I should receive the status code 200
    And The response should have a 'data.cep' property with the value '03310-000'
    And The response should have a 'data.logradouro' property with the value 'Rua Itapura'
    And The response should have a 'data.bairro' property with the value 'Vila Gomes Cardim'
    And The response should have a 'data.cidade' property with the value 'São Paulo'
    And The response should have a 'data.uf' property with the value 'SP'
