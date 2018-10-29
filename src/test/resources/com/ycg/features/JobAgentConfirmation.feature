Feature: Job alert
  As a candidate,
  I want to create job alerts,
  So that I can receive notifications about new open positions.

  @desktop @jobAlert
  Scenario: Create and confirm JobAgent
    Given  I am on the home page
    When   I perform search
    Then   JobAgent Popover is shown
    Then   I type random email into JobAgent Popover
    And    I save JobAgent from JobAgent Popover
    Then   Confirm JobAgent from email
    When   I click confirmation button
    Then   I am on Password Set modal
    And    I type password and create account on Password Set modal
    Then   I check performance