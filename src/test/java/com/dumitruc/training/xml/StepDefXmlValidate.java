package com.dumitruc.training.xml;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: dumitruc
 * Date: 20/05/14
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class StepDefXmlValidate {

    File xmlFile;
    File xsdFile;
    Boolean isValid;

    final String FILE_PRFX = "src\\test\\resources\\com\\dumitruc\\training\\xml\\";

    @Given("^we have a valid XML template (\\S+)$")
    public void haveXMLFile(String xmlFileName) throws Throwable {
        File xmlFile = new File(FILE_PRFX+xmlFileName);
        assertTrue("Could not read: "+xmlFile.getAbsolutePath(), xmlFile.canRead());
        this.xmlFile = xmlFile;
    }

    @When("^I validate the template as it is against the schema (\\S+)$")
    public void validateXmlTemplate(String xmlSchemaName) throws Throwable {
        File xsdFile = new File(FILE_PRFX+xmlSchemaName);
        assert (xmlFile.canRead());
        this.xsdFile = xsdFile;

        byte[] encodedXml = Files.readAllBytes(Paths.get(xsdFile.toURI()));
        String xmlString = new String(encodedXml);

        byte[] encodedXsd = Files.readAllBytes(Paths.get(xsdFile.toURI()));
        String xsdString = new String(encodedXsd);

        XmlValidator xmlValidator = new XmlValidator();

        isValid = xmlValidator.validateAgainstXSD(xmlString,xsdString);

    }

    @Then("^the validation responds with - valid xml$")
    public void checkIsValid() throws Throwable {
        assert (isValid != null && isValid);
    }

    @Given("^I have the designed schema (\\S+)$")
    public void iHaveSchema(String xmlSchemaName) throws Throwable {
        assertTrue("Not the same schema for which we checked the template",xsdFile.getCanonicalPath().equalsIgnoreCase(FILE_PRFX+xmlSchemaName));
    }

    @When("^I set the order quantity to (\\d+) in the XML$")
    public void setOrderQuantity(int arg1) throws Throwable {
        InputStream isXML = new FileInputStream(xmlFile);

    }

    @Then("^the schema validation accepts the input as valid$")
    public void the_schema_validation_accepts_the_input_as_valid() throws Throwable {
        System.out.println("something to do");
    }

    @Then("^the schema validation accepts the input as invalid$")
    public void the_schema_validation_accepts_the_input_as_invalid() throws Throwable {
        System.out.println("something to do");
    }
}
