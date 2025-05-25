Feature: New Employee Registration

  Scenario: Employee successfully registers
    Given Employee having email that is not registered
    When Send a http "POST" request to "/webhook/api/registe" with body: 
     """
      {}
    """
    Then The response status must be 200
    And The response body returns id

