Feature: Activity POS Tests

  @test
  Scenario: Open Activity POS and validate queue
  Given Activity POS application is launched
    When user logs in to Activity POS
    And user enters member code and submit it
    And user adds a new guest, enters the guest name, and verifies the name successfully
    And user select the club service
    When user click on Proceed button with user details and completes the Gate Entry
    Then user fetch the member data from database
    And user navigate to web Admin portal
    When user opens the Web Admin URL and logs in successfully
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

  @healthTest
  Scenario Outline: Open Activity POS and validate queue
    Given Activity POS application is launched
    When user logs in to Activity POS
    And user enters member code and submit it
    And user adds a new guest, enters the guest name, and verifies the name successfully
    And user select the club service
    And user click on Proceed button with user details and completes the Gate Entry
    And user select the activity "<Activity_Name>" on Dashboard page
    And user enters member code and submit it
    And user adds a new guest, enters the guest name, and verifies the name successfully
    And user select and verify the services as per the activity selection
    Then user click on Proceed button with user details and completes the Gate Entry
    Then user fetch the member data from database
    And user navigate to web Admin portal
    When user opens the Web Admin URL and logs in successfully
    And user Navigate to the Report section
    And user select the Report Header "finances"
    And user select the report list "Member Statements"
    And user enters the member code on the admin portal to access the report
    And user opens the new tab and verify the reports header
    Then user verifies the report, service, and sub-service details against the Activity "Health Club Gents"
    When user click on ecube logo icon
    And user Navigate to the Report section
    And user select the Report Header "sales"
    And user select the report list "Service Sales Detail"
    And user enters the member code on the admin portal to access the report
    And user opens the new tab and verify the reports header
    Then user verifies the report, service, and sub-service details against the Activity "Gate Entry"

    Examples: 
      | Activity_Name     |
      | Health Club Gents |
