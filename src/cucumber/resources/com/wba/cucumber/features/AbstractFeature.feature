Feature: Abstract features 
  This feature file verifies the functionality of all the micro-services endpoints 
  
 @Test1 
Scenario: 1
  Scenario1:Try to successfully retrieve a list of all the products from Azure Cosmos DB
  Given I send in a valid Rate Request
  Then I validate the response status code.