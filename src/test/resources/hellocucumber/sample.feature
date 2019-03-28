Feature: Is it Friday yet?
  Everybody wants to know when it's Friday

  Scenario: Sunday isn't Friday
    Given today is Sunday
    When I ask whether it's Friday yet
    Then I should be told "Nope"
    
    Scenario: Friday is Friday
    Given today is Friday
    When I ask whether it's Friday yet
    Then I should be told "Happy Friday"
    
    @browserscenerio
    Scenario: Finding Lego in Google
    Given I am on the google page
    When I search for "Lego"
    Then I should get the page title starting with "lego"
    
    
    