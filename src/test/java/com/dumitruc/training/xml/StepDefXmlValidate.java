package com.dumitruc.training.xml;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
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

    private String updatedXml;
    XmlHelper xmlHelper = new XmlHelper();

    @Given("^we have a valid XML template (\\S+)$")
    public void haveXMLFile(String xmlFileName) throws Throwable {
        File xmlFile = getLocalFile(xmlFileName);
        assertTrue("Could not read: " + xmlFile.getAbsolutePath(), xmlFile.canRead());
        this.xmlFile = xmlFile;
    }

    @And("^is valid against the schema (\\S+)$")
    public void validateXmlTemplate(String xmlSchemaName) throws Throwable {
        File xsdFile = getLocalFile(xmlSchemaName);
        assert (xmlFile.canRead());
        this.xsdFile = xsdFile;

        byte[] encodedXml = Files.readAllBytes(Paths.get(xmlFile.toURI()));
        String xmlString = new String(encodedXml);

        byte[] encodedXsd = Files.readAllBytes(Paths.get(xsdFile.toURI()));
        String xsdString = new String(encodedXsd);

        String validationError = xmlHelper.validateAgainstXSD(xmlString, xsdString);
        assertTrue(validationError == null);

    }

    @Then("^the schema validation accepts the input as (valid|invalid)$")
    public void checkResultXml(String expResult) throws Throwable {
        String validationError = xmlHelper.validateAgainstXSD(updatedXml, new Scanner(xsdFile).useDelimiter("\\A").next());

        if (expResult.equalsIgnoreCase("valid")) {
            assertTrue(validationError == null);
        } else {
            assertTrue(validationError != null && !validationError.isEmpty());
        }
    }

    @When("^I set the XML content to (\\S+) in (\\S+)$")
    public void updateXmlContent(String content, String path) throws Throwable {
        updatedXml = XmlHelper.editPath(FileUtils.readFileToString(xmlFile), path, content);
    }

    private File getLocalFile(String fileName) throws URISyntaxException {
        URI uri = this.getClass().getResource(fileName).toURI();
        return new File(uri);
    }
}
