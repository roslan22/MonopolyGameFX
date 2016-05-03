package com.monopoly.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class Utils
{
    private Utils()
    {
    }

    public static String getAbsolutePath(Class clazz, String relativePath)
    {
        try
        {
            System.out.println("path is : " + clazz.getResource(relativePath).toURI().toString());
            URL url = clazz.getResource(relativePath);
            InputStream iStream = clazz.getResourceAsStream(relativePath);
            
            return relativePath;
        } catch (URISyntaxException e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Integer tryParseInt(String num)
    {
        try
        {
            return Integer.parseUnsignedInt(num);
        } catch (NumberFormatException e)
        {
            return null;
        }
    }

    public static boolean validateXMLAgainstXSD(Class clazz, String xmlFilePath, String xsdFilePath)
    {
        try (InputStream xsdInputStream = clazz.getResourceAsStream(xsdFilePath);
             InputStream xmlInputStream = clazz.getResourceAsStream(xmlFilePath))
        {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsdInputStream));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlInputStream));
            return true;
        } catch (Exception ex)
        {
            return false;
        }
    }
}
