@uiSmoke
Feature: Dashboard Management
  test dashboard function for client

  Background:
    Given User has been login in
    When User clicks Add New Dashboard button
    And User fills in dashboard name with random value
    And User clicks Add button
    Then User should see message "Dashboard has been added"

  Scenario: Create new widget
  to confirm that create new widget function is working well
    Given User clicks Add new widget button
    And User chose first widget type
    And User chose "DEMO_FILTER"
    And User fills in widget name with random value
    And New widget should be created
    When User resize widget
    Then the widget should be resized