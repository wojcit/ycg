Feature: Job alert
  As a candidate,
  I want to create job alerts,
  So that I can receive notifications about new open positions.

  @desktop @jobAlert
  Scenario: Create and confirm JobAgent from popover
    Given  I am on the home page
    When   I perform search
    Then   JobAgent Popover is shown
    Then   I type random email into JobAgent Popover
    And    I save JobAgent from JobAgent Popover
    Then   Confirm JobAgent from email
    When   I click confirmation button
    Then   I am on Password Set modal
    And    I type password and create account on Password Set modal

  Scenario: Create and confirm JobAgent from C2A on resultlist
    Given  I am on the home page
    When   I perform search
    When   JobAgent Popover is shown
    Then   I close JobAgent Popover
    And    I click JobAgent C2A on Resultlist
    Then   I type random email into JobAgent Popover
    And    I save JobAgent from JobAgent Popover
    Then   Confirm JobAgent from email
    When   I click confirmation button
    Then   I am on Password Set modal
    And    I type password and create account on Password Set modal


  Scenario: Apply on random listing from resultlist
    Given I am on the home page
    When I perform search
    Then I am on resultlist
    And   JobAgent Popover is shown
    And   I close JobAgent Popover
    When I open random listing from resultlist
    Then I am on listing
    When I click Apply button
    Then I am on login page

