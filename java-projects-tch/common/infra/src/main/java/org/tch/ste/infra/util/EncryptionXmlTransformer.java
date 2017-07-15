package org.tch.ste.infra.util;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tch.ste.infra.constant.InfraConstants;
import org.tch.ste.infra.dto.EncryptionObject;
import org.tch.ste.infra.exception.SteException;

/**
 * @author sujathas
 * 
 */
public class EncryptionXmlTransformer {

    private static Logger logger = LoggerFactory.getLogger(EncryptionXmlTransformer.class);

    /**
     * @param value
     *            -Xml Value
     * @return - Returns Encryption Object
     * 
     */

    public static EncryptionObject getXmlData(String value) {

        EncryptionObject encObject = new EncryptionObject();
        try {
            byte[] byteArray = value.getBytes(InfraConstants.ENCODING_VALUE);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
            while (reader.hasNext()) {
                XMLEvent event = (XMLEvent) reader.next();
                if (event.isStartElement()) {
                    StartElement element = event.asStartElement();
                    if (InfraConstants.PARAM_REQUEST.equals(element.getName().getLocalPart())) {
                        @SuppressWarnings("rawtypes")
                        Iterator iterator = element.getAttributes();
                        while (iterator.hasNext()) {
                            Attribute attribute = (Attribute) iterator.next();
                            QName attributeAction = attribute.getName();
                            String attributeValue = attribute.getValue();
                            encObject.setAction(attributeAction.toString());
                            encObject.setValue(attributeValue);
                        }
                    }
                }
            }

        } catch (XMLStreamException | UnsupportedEncodingException e) {
            logger.warn("Unable to Fetch the Value", e);

        }

        return encObject;

    }

    /**
     * @param val
     *            - Value from Xml
     * @return -Returns the xml value parsed.
     * 
     * 
     */
    public static String readXml(String val) {
        String processedValue = null;
        try {
            byte[] byteArray = val.getBytes(InfraConstants.ENCODING_VALUE);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
            while (reader.hasNext()) {
                XMLEvent event = (XMLEvent) reader.next();
                if (event.isStartElement()) {
                    String element = event.asStartElement().getName().toString();
                    if (InfraConstants.PARAM_RESPONSE.equals(element)) {
                        event = reader.nextEvent();
                        processedValue = event.asCharacters().getData();
                    }
                }
            }

        } catch (UnsupportedEncodingException | XMLStreamException e) {
            logger.warn("Unable to Fetch the Value", e);

        }

        return processedValue;

    }

    /**
     * 
     * @param value
     *            -Value to be written onto XML
     * @param attrValue
     *            - Attribute Value
     * @return -Returns String in XML format
     * @throws SteException
     *             -Throws Exception
     * 
     */
    public static String generateXML(String value, String attrValue) throws SteException {
        StringWriter xmlValue = new StringWriter();
        XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLEventWriter writer;
        try {
            writer = outputFactory.createXMLEventWriter(xmlValue);
            XMLEvent event = xmlEventFactory.createStartDocument(InfraConstants.ENCODING_VALUE,
                    InfraConstants.XML_VERSION);
            writer.add(event);
            event = xmlEventFactory.createStartElement("", "", InfraConstants.PARAM_REQUEST);
            writer.add(event);
            event = xmlEventFactory.createAttribute(InfraConstants.XML_ATTRIBUTE, attrValue);
            writer.add(event);
            event = xmlEventFactory.createCharacters(value);
            writer.add(event);
            event = xmlEventFactory.createEndElement("", "", InfraConstants.PARAM_REQUEST);
            writer.add(event);
            event = xmlEventFactory.createEndDocument();
            writer.add(event);
            writer.flush();
            return xmlValue.getBuffer().toString();
        } catch (Exception e) {

            logger.warn("Unable to generate the Request XML for the input value" + value, e);
            throw new SteException("Unable to generate the Request XML for the input value" + value, e);

        }

    }

    /**
     * @param val
     *            -Value
     * @param attrValue
     *            -Attribute Value
     * @return -String
     * @throws SteException
     *             -SteException
     * @throws SteException
     *             -Exception
     */
    public static String generateResponse(String val, String attrValue) throws SteException {

        StringWriter xmlValue = new StringWriter();
        XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        XMLEventWriter writer;
        try {
            writer = outputFactory.createXMLEventWriter(xmlValue);
            XMLEvent event = xmlEventFactory.createStartDocument(InfraConstants.ENCODING_VALUE,
                    InfraConstants.XML_VERSION);
            writer.add(event);
            event = xmlEventFactory.createStartElement("", "", InfraConstants.PARAM_RESPONSE);
            writer.add(event);
            if (InfraConstants.ENCRYPT_VALUE.equals(attrValue)) {
                event = xmlEventFactory.createAttribute(InfraConstants.XML_TYPE, InfraConstants.XML_ATTR_VALUE);
                writer.add(event);
            } else if (InfraConstants.DECRYPT_VALUE.equals(attrValue)) {
                event = xmlEventFactory.createAttribute(InfraConstants.XML_TYPE, InfraConstants.XML_ATTR_VAL);
                writer.add(event);
            }
            event = xmlEventFactory.createCharacters(val);
            writer.add(event);
            event = xmlEventFactory.createEndElement("", "", InfraConstants.PARAM_RESPONSE);
            writer.add(event);
            event = xmlEventFactory.createEndDocument();
            writer.add(event);
            writer.flush();
            return xmlValue.getBuffer().toString();
        } catch (Exception e) {

            logger.warn("Unable to generate the Response XML for the Request Value " + val, e);
            throw new SteException("Unable to generate the Response XML for the Request Value" + val, e);

        }

    }

    /**
     * @param inputVal
     *            -xml value
     * @return -returns the processed input value
     * @throws SteException
     *             -steException
     * 
     */
    public static String parseInputXml(String inputVal) throws SteException {
        String inputValue = null;
        try {
            byte[] byteArray = inputVal.getBytes(InfraConstants.ENCODING_VALUE);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
            while (reader.hasNext()) {
                XMLEvent event = (XMLEvent) reader.next();
                if (event.isStartElement()) {
                    String element = event.asStartElement().getName().toString();
                    if (InfraConstants.PARAM_REQUEST.equals(element)) {
                        event = reader.nextEvent();
                        inputValue = event.asCharacters().getData();
                    }
                }
            }

        } catch (UnsupportedEncodingException | XMLStreamException e) {
            logger.warn("Unable to Fetch the Value", e);
            throw new SteException("Unable to Fetch the Value" + inputValue, e);

        }

        return inputValue;

    }
}
