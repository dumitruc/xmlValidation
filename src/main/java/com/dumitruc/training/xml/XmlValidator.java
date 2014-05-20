package com.dumitruc.training.xml;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: dumitruc
 * Date: 20/05/14
 * Time: 22:11
 * To change this template use File | Settings | File Templates.
 */
public class XmlValidator {

    public boolean validateAgainstXSD(String xml, String xsd){

        InputStream isXml = new ByteArrayInputStream(xml.getBytes());
        InputStream isXsd = new ByteArrayInputStream(xsd.getBytes());

        return validateAgainstXSD(isXml,isXsd);
    }

    private static boolean validateAgainstXSD(InputStream xml, InputStream xsd)
    {

        try
        {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }


}
