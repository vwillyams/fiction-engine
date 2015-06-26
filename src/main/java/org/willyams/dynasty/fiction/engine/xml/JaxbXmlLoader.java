package org.willyams.dynasty.fiction.engine.xml;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.willyams.dynasty.fiction.engine.events.EventCollection;
import org.xml.sax.SAXException;

public class JaxbXmlLoader implements EventFileLoader {

    private static final Logger logger = LoggerFactory.getLogger(JaxbXmlLoader.class);
    private final String filePath;

    public JaxbXmlLoader(String filePath) {
        this.filePath = filePath;
    }

    public JaxbXmlLoader() {
        this.filePath = "";
    }

    public EventCollection loadEventCollection(String filename) {
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File("target/schemas/event.xsd"));

            JAXBContext jc = JAXBContext.newInstance(EventCollection.class);

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new SimpleValidationEventHandler(JaxbValidationSeverity.ERROR));
            return (EventCollection) unmarshaller.unmarshal(new File(filePath + filename));
        } catch (JAXBException ex) {
            logger.error("File failed to load, filename " + filename, ex);
        } catch (SAXException ex) {
            logger.error("SAX parse exception loading schema", ex);
        }
        return new EventCollection();
    }

}
