@XML @TRAINING
Feature: XSD Schema validation
  As a test analyst
  I want to ensure that the designed schema satisfies the business requirements
  So that the system can receive/reject data based on defined requirements

  Scenario Outline: Validate multiple fields are validated correctly
    Given we have a valid XML template shiporder.xml
    And is valid against the schema <xsd file name>
    When I set the XML content to <content> in <path>
    Then the schema validation accepts the input as <type>
  Examples:
    | xsd file name | path               | content | type    |
    | shiporder.xsd | //quantity         | 4       | valid   |
    | shiporder.xsd | //quantity         | 0       | invalid |
    | shiporder.xsd | //quantity         | -1      | invalid |
    | shiporder.xsd | //quantity         | tg      | invalid |
    | shiporder.xsd | //item/quantity[0] | 3       | valid   |



