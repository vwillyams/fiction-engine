package org.willyams.dynasty.fiction.engine.events;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.willyams.dynasty.fiction.engine.constants.TestConstants;
import org.willyams.dynasty.fiction.engine.xml.JaxbXmlLoader;
import org.willyams.dynasty.fiction.engine.xml.EventFileLoader;

import static junit.framework.Assert.assertEquals;

public class SimpleEventParsingTest extends TestCase {

    private static final EventFileLoader loader = new JaxbXmlLoader(TestConstants.EVENT_XML_LOCATION);

    public static Test suite() {
        return new TestSuite(SimpleEventParsingTest.class);
    }

    public void testSimpleImport() {
        EventCollection collection = loader.loadEventCollection("TestEvent001.xml");
        Event event = collection.get(0);
        assertEquals("TEST_EVENT_001", event.getId());
    }

    public void testHarderImport() {
        EventCollection collection = loader.loadEventCollection("TestEvent002.xml");
        Event event = collection.get(0);
        assertEquals("TEST_EVENT_002", event.getId());
        assertEquals("Second Test Event", event.getName());
        assertEquals("This is the second test event", event.getDescription());
        assertTrue(event.getOptions().contains(Option.DEFAULT_OPTION));
        assertTrue(event.getOptions().size() == 1);
    }

    public void testImportWithOptions() {
        EventCollection collection = loader.loadEventCollection("TestEvent003.xml");
        Event event = collection.get(0);
        assertEquals("TEST_EVENT_003", event.getId());
        assertEquals("Third Test Event", event.getName());
        assertEquals("This test event includes options", event.getDescription());
        assertFalse(event.getOptions().isEmpty());
        assertEquals("Option 1", event.getOptions().get(0).getDisplayText());
        assertEquals("Option 2", event.getOptions().get(1).getDisplayText());
    }

    public void testFailedImport() {
        EventCollection collection = loader.loadEventCollection("TestEvent004.xml");
        assertTrue(collection.isEmpty());
    }

    public void testAlternativeFailedImport() {
        EventCollection collection = loader.loadEventCollection("TestEvent005.xml");
        assertTrue(collection.isEmpty());
    }

    public void testCompositeIdentifier() {
        EventCollection collection = loader.loadEventCollection("TestEvent006.xml");
        assertFalse(collection.isEmpty());
        Event event = collection.get(0);
        assertEquals("TEST.EVENT_003", event.getId());
        assertEquals("Third Test Event", event.getName());
        assertEquals("This test event includes options", event.getDescription());
        assertFalse(event.getOptions().isEmpty());
        assertEquals("Option 1", event.getOptions().get(0).getDisplayText());
        assertEquals("Option 2", event.getOptions().get(1).getDisplayText());
    }
}
