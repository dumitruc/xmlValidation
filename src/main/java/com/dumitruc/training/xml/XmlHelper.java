package com.dumitruc.training.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by dima on 01/11/2014.
 */
public class XmlHelper {


    public static String editPath(String xmlSource, String xPath, String newValue) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException, TransformerException {
        Document xmlDoc = readString(xmlSource);
        XPathExpression expr = evaluateThePath(xPath);
        NodeList nl = (NodeList) expr.evaluate(xmlDoc, XPathConstants.NODESET);
        for (int i = nl.getLength(); i > 0; i--) {
            nl.item(0).setNodeValue(newValue);
        }
        return xmlDocToString(xmlDoc);
    }

    public static String xmlDocToString(Document document) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        return writer.getBuffer().toString();
    }

    private static XPathExpression evaluateThePath(String xPath) throws XPathExpressionException {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        return xpath.compile(xPath + "/text()");//small hack to get to the actual value as this is what we actually need and is mostly used
    }

    private static Document readString(String xmlSource) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        return docBuilder.parse(new ByteArrayInputStream(xmlSource.getBytes(StandardCharsets.UTF_8)));
    }

    public static String validateAgainstXSD(String xml, String xsd) throws IOException {

        InputStream isXml = new ByteArrayInputStream(xml.getBytes());
        InputStream isXsd = new ByteArrayInputStream(xsd.getBytes());

        return isXmlPassingSchemaValidation(isXml, isXsd);
    }

    private static String isXmlPassingSchemaValidation(InputStream xml, InputStream xsd) throws IOException {
        Source xmlSource = new StreamSource(xml);

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(xmlSource);
            return null;
        } catch (SAXException e) {
            return e.toString();
        }

    }

}
