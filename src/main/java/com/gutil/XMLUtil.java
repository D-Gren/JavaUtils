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

/**
 * Utility class for operations on XML documents.
 * @author Dariusz Gren
 * @version 1.0
 */
public class XMLUtil {

    public static final int DEFAULT_INDENT = 4;

    /**
     * Creates new empty XML document.
     * @return empty XML document
     */
    public static Document createNewDocument() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return builder.newDocument();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Could not create new XML document", e);
        }
    }

    /**
     * Creates new empty XML document with root element.
     * @param rootTag tag of the root element
     * @return empty XML document with root element
     */
    public static Document createNewDocumentWithRoot(String rootTag) {
        Document document = createNewDocument();
        Element root = document.createElement(rootTag);
        document.appendChild(root);
        return document;
    }

    /**
     * Creates default {@code Transformer} with indentation set to value of the {@code indent} parameter.
     * @param indent length of indentation
     * @return default {@code Transformer} for working with XML documents
     */
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

    /**
     * Loads XML document from the specific file.
     * @param file file from which document should be loaded
     * @return XML document as {@link Document} object
     */
    public static Document loadDocumentFromFile(File file) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return documentBuilder.parse(file);
        } catch (Exception e) {
            throw new RuntimeException("Could not load XML document from file " + file.getAbsolutePath() + ".", e);
        }
    }

    /**
     * Returns root element of the XML document. If document is null, no exception is thrown and null is returned.
     * @param document document from which root element should be returned
     * @return root element of the document
     */
    public static Element getRootElement(Document document) {
        return document != null ? document.getDocumentElement() : null;
    }

    /**
     * Converts XML document to its {@code String} representation using default XML {@link Transformer}.
     * @param document document to be converted
     * @return {@code String} representation of the XML document
     */
    public static String toString(Document document) {
        return toString(document, createDefaultTransformer(DEFAULT_INDENT));
    }

    /**
     * Converts XML document to its {@code String} representation using specific {@code Transformer} implementation.
     * @param document document to be converted
     * @param transformer {@code Transformer} to be used for conversion
     * @return {@code String} representation of the XML document
     */
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

    /**
     * Saves XML document to the specific {@code File} using the default {@link Transformer}.
     * @param document document to be saved
     * @param file file in the file system to which the document should be saved
     */
    public static void saveDocument(Document document, File file) {
        saveDocument(document, file, DEFAULT_INDENT);
    }

    /**
     * Saves XML document to the specific {@code File} with a specific size of the indentation.
     * @param document document to be saved
     * @param file file in the file system to which the document should be saved
     * @param indent length of indentation
     */
    public static void saveDocument(Document document, File file, int indent) {
        saveDocument(document, file, createDefaultTransformer(indent));
    }

    /**
     * Saves XML document to the specific {@code File} using a specific implementation of the {@link Transformer} class.
     * @param document document to be saved
     * @param file file in the file system to which the document should be saved
     * @param transformer implementation of the {@link Transformer} to be used for saving
     */
    public static void saveDocument(Document document, File file, Transformer transformer) {
        try(FileOutputStream output = new FileOutputStream(file)) {
            stripElement(document.getDocumentElement());
            transform(document, transformer, output);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving XML document.", e);
        }
    }

    /**
     * Transforms the XML document.
     * @param document document to be transformed
     * @param transformer {@code Transformer} to be used for the transformation
     * @param output output for transformation
     */
    private static void transform(Document document, Transformer transformer, OutputStream output) throws TransformerException {
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(output);
        transformer.transform(source, result);
    }

    /**
     * Transforms the XML document.
     * @param document document to be transformed
     * @param transformer {@code Transformer} to be used for the transformation
     * @param writer writer for transformation
     */
    private static void transform(Document document, Transformer transformer, Writer writer) throws TransformerException {
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
    }

    /**
     * Strips XML element by removing {@linkplain Character#isWhitespace(int) white spaces}.
     * @param element element to be stripped
     */
    private static void stripElement(Element element) {
        if (element == null) {
            return;
        }

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