@mass
Feature: Update Store Attributes for Scan&Pay
  Purpose to use Mass Portal to update the store Attributes 

  Scenario: Login into Massportal
    Given I am on Massportal "home" page
    When I enter the "username" and "password"
    And click log in button
    Then I should be in massportal main page "MASS"
   
  @selectDatacenter
  Scenario: Select the environment in mass portal
    Given I am in the massportal main page with pageTitle as "MASS"
    And    DataCenter select should be clickable 
    When  DataCenter select having "DAL" and "WDC" value in it
    Then  Choose "DAL" as a first step
    
    
 @basicsetup
 Scenario: Basic setup for Cache update
 	    Given I am in the massportal main page with pageTitle as "MASS"
 	    When URL-Dropdown having the value "scp_cell"
 	    Then Choose "FCC|DAL|scp_cell" and trigger change button
 	    And  Select CacheSearch
 	    And  Select StoreAttributes
 	  
 @inputstoreAndUpdate
 Scenario: Input Store number and confirm the attribute specific to store 
 		  Given "Store Attributes" selected in the dropdown
 		  When Input store_id 
 		  And  Trigger search 
 		  Then Verify the selected store number
 
 