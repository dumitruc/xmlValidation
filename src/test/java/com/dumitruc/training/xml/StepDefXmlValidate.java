package com.dumitruc.training.xml;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import javax.xml.transform.Source;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

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

    final String FILE_PRFX = "src\\test\\resources\\com\\dumitruc\\training\\xml\\";
    private String updatedXml;

    @Given("^we have a valid XML template (\\S+)$")
    public void haveXMLFile(String xmlFileName) throws Throwable {
        File xmlFile = new File(FILE_PRFX + xmlFileName);
        assertTrue("Could not read: " + xmlFile.getAbsolutePath(), xmlFile.canRead());
        this.xmlFile = xmlFile;
    }

    @And("^is valid against the schema (\\S+)$")
    public void validateXmlTemplate(String xmlSchemaName) throws Throwable {
        File xsdFile = new File(FILE_PRFX + xmlSchemaName);
        assert (xmlFile.canRead());
        this.xsdFile = xsdFile;

        byte[] encodedXml = Files.readAllBytes(Paths.get(xmlFile.toURI()));
        String xmlString = new String(encodedXml);

        byte[] encodedXsd = Files.readAllBytes(Paths.get(xsdFile.toURI()));
        String xsdString = new String(encodedXsd);

        XmlValidator xmlValidator = new XmlValidator();

        Boolean isValid = xmlValidator.validateAgainstXSD(xmlString, xsdString);
        assert (isValid);

    }


    @When("^I set the order quantity to (\\S+) in the XML$")
    public void setOrderQuantity(String quantity) throws Throwable {
        Scanner scanner = new Scanner(xmlFile).useDelimiter("\\A");
        String xmlFileContent = "";
        if (scanner.hasNext()) {
            xmlFileContent = scanner.next();
        }

        updatedXml = xmlFileContent.replace("<quantity>1</quantity>", "<quantity>" + quantity + "</quantity>");
    }

    @Then("^the schema validation accepts the input as (valid|invalid)$")
    public void checkResultXml(String expResult) throws Throwable {
        XmlValidator xmlValidator = new XmlValidator();
        Boolean isValid = xmlValidator.validateAgainstXSD(updatedXml, new Scanner(xsdFile).useDelimiter("\\A").next());

        if (expResult.equalsIgnoreCase("valid")) {
            assertTrue(isValid);
        } else {
            assertTrue(!isValid);
        }
    }

    @When("^I set the XML content to (\\S+) in (\\S+)$")
    public void updateXmlContent(String content, String path) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new cucumber.runtime.PendingException();
    }
}
