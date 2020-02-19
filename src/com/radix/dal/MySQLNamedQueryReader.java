package com.radix.dal;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

public class MySQLNamedQueryReader implements NamedQueryReader {

    private static final String FILENAME = "config/mysql-named-query.xml";

    /**
     * TODO : Fetch path from content as per given named query 
     * @param document
     * @param xpathExpression
     * @return
     * @throws Exception
     */
    private static String evaluateXPath(Document document, String xpathExpression) throws Exception {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        try {
            XPathExpression expr = xpath.compile(xpathExpression);
            return (String) expr.evaluate(document, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * TODO : fetch content from XML file 
     * @param fileName
     * @return
     * @throws Exception
     */
    private static Document getDocument(String fileName) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(fileName);
        return doc;
    }

    /**
     * TODO : Read XML file and filter query based on given named query
     */
    @Override
    public String readXMLfile(String nameQuery) throws PersistenceException {
        try {
            Document document = getDocument(FILENAME);
            String xpathExpression = "/querylist/namedquery[contains(@name,'" + nameQuery + "')]";
            return evaluateXPath(document, xpathExpression);
        } catch (JAXBException ex) {
            throw new PersistenceException(ex);
        } catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }

}
