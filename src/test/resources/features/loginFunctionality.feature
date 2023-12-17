@uiSmoke
Feature: Login
  test login function for client

  Scenario Outline: LoginPage
  to confirm that login function works well
    Given User opens ReportPortal login page
    When User inputs userName with "<User name>"
    And User inputs password with "<Password>"
    And User clicks Sign In button
    Then User should see alert "<Result>"

    Examples:
      | User name     | Password      | Result         |
      | validUserName | validPassword | successSignIn  |
      | validUserName | randomString  | badCredentials |
      | randomString  | validPassword | badCredentials |
