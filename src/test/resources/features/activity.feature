Feature: Activity POS Tests

  @test
  Scenario: Open Activity POS and validate queue
    #Given Activity POS application is launched
    When user logs in to Activity POS
    And user enters member code and submit it
    And User adds a new guest, enters the guest name, and verifies the name successfully
    And User select the club service
    And User click on Proceed button with user details and completes the member entry
    Then User fetch the member data from database
    And User navigate and log in to web Admin portal to view the Reports
    And User Navigate to the Report section 
    And User select the Report Header "finances"
    And User select the report list "Member Statements"
    And User enters the member code on the admin portal to access the report
    And User opens the new tab and verify the reports header
    Then user verifies the report, service, and sub-service details against the Activity "Gate Entry"
    And User click on ecube logo icon
    And User Navigate to the Report section 
    And User select the Report Header "sales"
    And User select the report list "Service Sales Detail"
    And User enters the member code on the admin portal to access the report
    And User opens the new tab and verify the reports header
    And user verifies the report, service, and sub-service details against the Activity "Gate Entry"
