  Feature: Add, Update and Delete Data Object
  Scenario: Add New Object Successfully
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
    And Save the id from the response to local storage

   Scenario: Update Object Partially Successfully
    Given Make sure id in local storage not empty 1
    When Send a request to update object
    Then The response status update object must be 200
    And The response schema update object should be match with schema "updateobject-schema.json"
    And Name in the response update object changed to "Macbook Pro Yuana Updated"
    And Year in the response update object changed to "2020"

 Scenario: Check Object successfully updated
    Given Make sure id in local storage not empty 2
    When Send a request to get single object after update
    Then The response status get single object must be 200
    And Name in the response get single object must be "Macbook Pro Yuana Updated"
    And Year in the response get single object must be "2020"

   Scenario: Delete Object Successfully
    Given Make sure id in local storage not empty 3
    When Send a request to delete object
    Then The response status delete object must be 200 