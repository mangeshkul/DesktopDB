Feature: Res POS Tests

  Scenario: Open Res POS and perform basic flow
    Given Res POS application is launched
    When user navigates to POS module
    Then POS home should be displayed