Feature: Activity POS Tests

  @test
  Scenario: Open Activity POS and validate queue
    Given user logs in to Activity POS
    And user enters member code and submit it
    And user adds a new guest, enters the guest name, and verifies the name successfully
    And user select the club service
    When user click on Proceed button with user details and completes the member entry
    Then user fetch the member data from database
    When user navigate and log in to web Admin portal to view the Reports
    And user Navigate to the Report section
    And user select the Report Header "finances"
    And user select the report list "Member Statements"
    And user enters the member code on the admin portal to access the report
    And user opens the new tab and verify the reports header
    Then user verifies the report, service, and sub-service details against the Activity "Gate Entry"
    When user click on ecube logo icon
    And user Navigate to the Report section
    And user select the Report Header "sales"
    And user select the report list "Service Sales Detail"
    And user enters the member code on the admin portal to access the report
    And user opens the new tab and verify the reports header
    Then user verifies the report, service, and sub-service details against the Activity "Gate Entry"
