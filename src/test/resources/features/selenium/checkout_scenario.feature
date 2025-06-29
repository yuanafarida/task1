Feature: Purchase the order from ecommerce

Background: User landed to login page
    Given User landing to logged ecommerce and checkout
    When User input username "standard_user" and password "secret_sauce"
    Then User redirect to product page and see page title Products
    When User add first product to Cart "Sauce Labs Fleece Jacket"
    Then Verify first product successfully added on cart page "Sauce Labs Fleece Jacket"
    When User continue shopping and add second product to Cart "Sauce Labs Onesie"
    Then Verify second product successfully added on cart page "Sauce Labs Onesie"
    When User click on checkout button
    Then User redirect to checkout page and see page title Checkout: Your Information
    
Scenario Outline: User input the information page with invalid data
    When User input first name "<first_name>" and last name "<last_name>" and zip code "<zip_code>"
    Then Verify error message "<error_message>" is displayed
    Examples:
    |first_name           | last_name        | zip_code | error_message |
    |                     | Satu             |123456    | Error: First Name is required  |
    |Customer             |                  |123456    | Error: Last Name is required   |        
    |Customer             | Satu             |          | Error: Postal Code is required |
        
Scenario: User input the information page with valid data
    When User input first name "Customer" and last name "Satu" and zip code "123456"
    Then User redirect to overview page and see page title Checkout: Overview
    When User on overview page
    Then Verify the order summary is displayed
    When User click finish button
    Then User redirect to completion page and see message displayed Thank you for your order!
    And User click back to home button