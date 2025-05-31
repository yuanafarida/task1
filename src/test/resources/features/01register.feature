Feature: New Employee Registration and Login

  Scenario: Employee successfully registers
    When Send employee to register with body: 
     """
      {
        "email": "yuanafarida006@test.com",
        "password": "@dmin123",
        "full_name": "Yuana Test",
        "department": "Technology",
        "phone_number": "082264189134"
      }
    """
    Then The response status register must be 200
    And The response body register returns id
    And The response schema register should be match with schema "register-schema.json"

  Scenario: Employee successfully login using registered email and password
    When Send employee to login with body: 
     """
      {
        "email": "yuanafarida006@test.com",
        "password": "@dmin123"
      }
    """
    Then The response status login must be 200
    And The response body login returns token
    And Save the token from the response to local storage

  Scenario: Get All Department using token
    Given Make sure token in local storage not empty 1
    When Using token from local storage to get all departments
    Then The response status get all departments must be 200
    And The response schema all departments should be match with schema "department-schema.json"