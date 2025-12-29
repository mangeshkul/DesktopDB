Feature: Activity POS Tests

  @test1
  Scenario: Verify Gate entry for Member, Sub-Member(s) and Guest(s) their total Amount and Reports.
    Given Activity POS application is launched
    When user logs in to Activity POS
    And user enters member code and submit it
    And user adds a new guest, enters the guest name, and verifies the name successfully
    And user select the club service for Gate Entry
    And user verifies and gets the net amount
    When user click on Proceed button with user details and completes the Gate Entry and Verifies it
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

  @healthTest @test
  Scenario Outline: Verify any Amenity Access for Member, Sub-Member(s) and Guest(s) their total Amount and Reports for Helath Club
    Given Activity POS application is launched
    When user logs in to Activity POS
    And user enters member code and submit it
    And user adds a new guest, enters the guest name, and verifies the name successfully
    And user select the club service for Gate Entry
    And user click on Proceed button with user details and completes the Gate Entry and Verifies it
    And user select the activity "<Activity_Name>" on Dashboard page
    And user enters member code and submit it
    And user adds a new guest, enters the guest name, and verifies the name successfully
    And user select and verify the services as per the activity selection
    And user verifies and gets the net amount
    Then user click on Proceed button with user details and completes the Gate Entry and Verifies it
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
    Then user verifies the report, service, and sub-service details against the Activity "Health Club Gents"

    Examples: 
      | Activity_Name     |
      | Health Club Gents |

  @Badminton
  Scenario Outline: Verify any Amenity Access for Member, Sub-Member(s) and Guest(s) their total Amount and Reports for Badminton
    Given Activity POS application is launched
    When user logs in to Activity POS
    And user enters member code and submit it
    And user adds a new guest, enters the guest name, and verifies the name successfully
    And user select the club service for Gate Entry
    And user click on Proceed button with user details and completes the Gate Entry and Verifies it
    And user select the activity "<Activity_Name>" on Dashboard page
    And user enters member code and submit it
    And user adds a new guest, enters the guest name, and verifies the name successfully
    And user select and verify the services as per the activity selection
    And user verifies and gets the net amount
    Then user click on Proceed button with user details and completes the Gate Entry and Verifies it
    Then user fetch the member data from database
    And user navigate to web Admin portal
    When user opens the Web Admin URL and logs in successfully
    And user Navigate to the Report section
    And user select the Report Header "finances"
    And user select the report list "Member Statements"
    And user enters the member code on the admin portal to access the report
    And user opens the new tab and verify the reports header
    Then user verifies the report, service, and sub-service details against the Activity "Badminton Sports"
    When user click on ecube logo icon
    And user Navigate to the Report section
    And user select the Report Header "sales"
    And user select the report list "Service Sales Detail"
    And user enters the member code on the admin portal to access the report
    And user opens the new tab and verify the reports header
    Then user verifies the report, service, and sub-service details against the Activity "Badminton Sports"

    Examples: 
      | Activity_Name    |
      | Badminton Sports |

  @subMember
  Scenario Outline: Verify any Badminton for Member, Sub-Member(s) with Discount, their total Amount and Reports.
    Given Activity POS application is launched
    When user logs in to Activity POS
    And user enters member code and submit it
    And user selects an available sub member
    And user select the club service for Gate Entry
    And user click on Proceed button with user details and completes the Gate Entry and Verifies it
    And user select the activity "<Activity_Name>" on Dashboard page
    And user enters member code and submit it
    And user selects an available sub member
    And user select and verify the services as per the activity selection
    And user verifies and gets the net amount
    Then user click on Proceed button with user details and completes the Gate Entry and Verifies it
    Then user fetch the member data from database
    And user navigate to web Admin portal
    When user opens the Web Admin URL and logs in successfully
    And user Navigate to the Report section
    And user select the Report Header "finances"
    And user select the report list "Member Statements"
    And user enters the member code on the admin portal to access the report
    And user opens the new tab and verify the reports header
    Then user verifies the report, service, and sub-service details against the Activity "Badminton Sports"
    When user click on ecube logo icon
    And user Navigate to the Report section
    And user select the Report Header "sales"
    And user select the report list "Service Sales Detail"
    And user enters the member code on the admin portal to access the report
    And user opens the new tab and verify the reports header
    Then user verifies the report, service, and sub-service details against the Activity "Badminton Sports"

    Examples: 
      | Activity_Name    |
      | Badminton Sports |
