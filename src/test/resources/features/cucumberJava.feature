@resetDataBase
Feature: customer statement feature

  Scenario: client makes a correct POST /customer/statement
    Given a customer statement with unique reference and correct end balance
    When a post is done to /customer/statement
    Then a status code of 200 is expected
    And result is SUCCESSFUL

  Scenario: client makes a POST /customer/statement with incorrect end balance
    Given a customer statement with unique reference and incorrect end balance
    When a post is done to /customer/statement
    Then a status code of 200 is expected
    And result is INCORRECT_END_BALANCE

  Scenario: client makes a POST /customer/statement with duplicate reference
    Given a customer statement with duplicate reference and correct end balance
    When a post is done to /customer/statement
    Then a status code of 200 is expected
    And result is DUPLICATE_REFERENCE

  Scenario: client makes a POST /customer/statement with duplicate reference and incorrect balance
    Given a customer statement with duplicate reference and incorrect end balance
    When a post is done to /customer/statement
    Then a status code of 200 is expected
    And result is DUPLICATE_REFERENCE_INCORRECT_END_BALANCE

