@uiSmoke
Feature: Dashboard Management
  test dashboard function for client

  Background:
    Given User has been login in
    When User clicks Add New Dashboard button
    And User fills in dashboard name with random value
    And User clicks Add button
    Then User should see message "Dashboard has been added"

  Scenario: Update dashboard name
  to confirm that update dashboard name function works well
    When User clicks edit to update the dashboard
    And User updates dashboard name with random value
    And User clicks the Update button
    Then Dashboard name should be changed

  Scenario: Delete new dashboard
  to confirm that delete new dashboard function works well
    When User clicks Delete button
    And User confirms deletion
    Then User should see message "Dashboard has been deleted"
