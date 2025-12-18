Feature: Admin Web Login

  Scenario: Valid login to Admin module
    Given user launches the Admin Web application
    When user enters username as "testuser" and password as "Password123"
    And clicks on Login button
    Then user should be navigated to the Dashboard