@tag
Feature: Verify the basic operations in online calculator 
  I want to use this template for my feature file

  @tag1
  Scenario Outline: Verify the calculation of two numbers
    Given Open chrome browser and start application
    When 	Enter the values with desired operator "<value1>" "<value2>" "<operator>"
    Then  I should be able to see the output as "<expected>"
    Examples:
    |	value1	|	value2	| operator	|	expected	|
    |	4				|	5				|		+				|		9				|
    |	6				|	2				|		-				|		4				|
    |	8				|	4				|		*				|		32			|
    |	8				|	9				|		+				|		13			|
   


   
   