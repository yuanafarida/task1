  Feature: Add, Update and Delete Data Object
  Scenario:
    Given Make sure token in local storage not empty 2
    When Send a request to add object
    Then The response status add object must be 200
    And The response schema add object should be match with schema "addobject-schema.json"
    And Year in the response add object must be 2025
    And Price in the response add object must be 1000
    And CPU Model in the response add object must be "Intel Core I3"
    And Hard Disk Size in the response add object must be "500 GB"
    And Capacity in the response add object must be "2 cpu"
    And Screen Size in the response add object must be "15 Inchi"
    And Color in the response add object must be "Red"