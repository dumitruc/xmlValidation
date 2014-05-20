@wip @xml
Feature: XSD Schema validation
  As a test analyst
  I want to ensure that the designed schema satisfies the business requirements
  So that the system can receive/reject data based on defined requirements

#Source materials
#Schema: http://www.w3schools.com/schema/schema_example.asp

  Background: Existing valid XML template
    Given we have a valid XML template shiporder.xml
    When I validate the template as it is against the schema shiporder.xsd
    Then the validation responds with - valid xml

  Scenario Outline: Validate the schema checks the quantity correctly
    Given I have the designed schema <xsd file name>
    When I set the order quantity to <order quantity> in the XML
    Then the schema validation accepts the input as <type>
  Examples:
    | xsd file name | order quantity | type    |
    | shiporder.xsd | 4              | valid   |
    | shiporder.xsd | -1             | invalid |



