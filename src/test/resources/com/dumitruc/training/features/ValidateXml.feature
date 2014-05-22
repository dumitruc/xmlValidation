@xml
Feature: XSD Schema validation
  As a test analyst
  I want to ensure that the designed schema satisfies the business requirements
  So that the system can receive/reject data based on defined requirements

#Source materials
#Schema: http://www.w3schools.com/schema/schema_example.asp

  Scenario Outline: Validate the schema checks the quantity correctly
    Given we have a valid XML template shiporder.xml
    And is valid against the schema <xsd file name>
    When I set the order quantity to <order quantity> in the XML
    Then the schema validation accepts the input as <type>
  Examples:
    | xsd file name | order quantity | type    |
    | shiporder.xsd | 4              | valid   |
    | shiporder.xsd | -1             | invalid |



