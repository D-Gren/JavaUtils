package com.gutil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

public class XMLUtilTest {

    @Test
    public void toStringTest() throws ParserConfigurationException {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", XMLUtil.toString(document));

        Element root = document.createElement("root");
        document.appendChild(root);
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + System.lineSeparator() + "<root/>", XMLUtil.toString(document));

        Element child = document.createElement("child");
        root.appendChild(child);
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + System.lineSeparator() + "<root>" + System.lineSeparator() + "    <child/>" + System.lineSeparator() + "</root>", XMLUtil.toString(document));
    }

    @Test
    public void newDocumentTest() {
        Document document = XMLUtil.createNewDocument();
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", XMLUtil.toString(document));

        Document documentWithRoot = XMLUtil.createNewDocumentWithRoot("my_root");
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + System.lineSeparator() + "<my_root/>", XMLUtil.toString(documentWithRoot));
    }

    @Test
    public void loadAndSaveTest() {
        File expectedDocumentFile = new File(XMLUtilTest.class.getResource("/ExpectedXMLDefaultTransformer.xml").getFile());
        Document expectedDocument = XMLUtil.loadDocumentFromFile(expectedDocumentFile);
        Assertions.assertNotNull(expectedDocument);

        Document actualDocument = XMLUtil.createNewDocument();
        Element root = actualDocument.createElement("root");
        Element child = actualDocument.createElement("child");
        child.setAttribute("attr", "test");
        root.appendChild(child);
        actualDocument.appendChild(root);
        Assertions.assertEquals(XMLUtil.toString(expectedDocument), XMLUtil.toString(actualDocument));
        Assertions.assertEquals(root.getTagName(), XMLUtil.getRootElement(actualDocument).getTagName());

        File actualDocumentFile = new File(expectedDocumentFile.getParentFile(), "ActualXMLDefaultTransformer.xml");
        Assertions.assertFalse(actualDocumentFile.exists());

        try {
            XMLUtil.saveDocument(actualDocument, actualDocumentFile);
            Assertions.assertTrue(actualDocumentFile.exists());

            Document actualDocumentLoaded = XMLUtil.loadDocumentFromFile(actualDocumentFile);
            Assertions.assertEquals(XMLUtil.toString(expectedDocument), XMLUtil.toString(actualDocumentLoaded));
        } finally {
            if (actualDocumentFile.exists()) {
                actualDocumentFile.delete();
            }
        }
    }

}
