package com.gutil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

/***
 * Utility class for operations on XML documents.
 * @author Dariusz Gren
 * @version 1.0
 */
public class XMLUtil {

    public static final int DEFAULT_INDENT = 4;

    public static Document createNewDocument() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return builder.newDocument();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Could not create new XML document", e);
        }
    }

    public static Document createNewDocument(String rootTag) {
        Document document = createNewDocument();
        Element root = document.createElement(rootTag);
        document.appendChild(root);
        return document;
    }

    public static Transformer createDefaultTransformer(int indent) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            return transformer;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating default XML transformer.", e);
        }
    }

    public static Document loadDocumentFromFile(File file) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return documentBuilder.parse(file);
        } catch (Exception e) {
            throw new RuntimeException("Could not load XML document from file " + file.getAbsolutePath() + ".", e);
        }
    }

    public static Element getRootElement(Document document) {
        return document.getDocumentElement();
    }

    public static String toString(Document document) {
        return toString(document, createDefaultTransformer(DEFAULT_INDENT));
    }

    public static String toString(Document document, Transformer transformer) {
        try {
            Element root = getRootElement(document);
            stripElement(root);

            StringWriter writer = new StringWriter();
            transform(document, transformer, writer);
            return writer.toString().strip();
        } catch (Exception e) {
            throw new RuntimeException("Could not convert XML document to String.", e);
        }
    }

    public static void saveDocument(Document document, File file) {
        saveDocument(document, file, DEFAULT_INDENT);
    }

    public static void saveDocument(Document document, File file, int indent) {
        saveDocument(document, file, createDefaultTransformer(indent));
    }

    public static void saveDocument(Document document, File file, Transformer transformer) {
        try(FileOutputStream output = new FileOutputStream(file)) {
            stripElement(document.getDocumentElement());
            transform(document, transformer, output);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving XML document.", e);
        }
    }

    private static void transform(Document document, Transformer transformer, OutputStream output) throws TransformerException {
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }

    private static void transform(Document document, Transformer transformer, Writer writer) throws TransformerException {
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
    }

    private static void stripElement(Element element) {
        NodeList children = element.getChildNodes();
        for (int i = children.getLength() - 1; i >= 0; i--) {
            Node child = children.item(i);
            if (child instanceof Text text && text.getData().isBlank()) {
                element.removeChild(child);
            } else if (child instanceof Element childElement) {
                stripElement(childElement);
            }
        }
    }

}