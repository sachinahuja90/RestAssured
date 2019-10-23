Feature: List API

  @FilterPage
  Scenario: Validate that a user is able to get Lists using WunderLink API
    Given Rest Request with base URL "https://a.wunderlist.com"
    And provide headers to the rest request    
    When Apply "Get" operation on resource "/api/v1/lists"
    Then Status code should be 200