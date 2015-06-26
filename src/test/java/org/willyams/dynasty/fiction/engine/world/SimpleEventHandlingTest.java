package org.willyams.dynasty.fiction.engine.world;

import java.util.List;
import org.willyams.dynasty.fiction.engine.constants.TestConstants;
import org.willyams.dynasty.fiction.engine.events.Event;
import org.willyams.dynasty.fiction.engine.xml.JaxbXmlLoader;
import org.willyams.dynasty.fiction.engine.xml.EventFileLoader;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class SimpleEventHandlingTest {

    private static final EventFileLoader loader = new JaxbXmlLoader(TestConstants.EVENT_XML_LOCATION);

    private WorldState world;
    private Actor testActor;

    public SimpleEventHandlingTest() {
        world = WorldState.instance();
        testActor = new Actor();
        world.addActor(testActor);
    }

    public void setUp(String fileName) {
        testActor.clearEvents();
        world.clearEvents();
        world.addEvents(loader.loadEventCollection(fileName));
    }

    public void testSimpleEvents() {
        setUp("TestEvent003.xml");
        world.generateEventsForActors();
        assertTrue(testActor.hasUnresolvedEvents());
        world.resolveActiveEvents();
        assertFalse(testActor.hasUnresolvedEvents());
    }

    public void testIndividualEventHandling() {
        setUp("TestEvent003.xml");
        world.generateEventsForActors();
        assertTrue(testActor.hasUnresolvedEvents());
        List<Event> events = testActor.getEvents();
        testActor.handleEvent(events.get(0), events.get(0).getOptions().get(0), true);
        assertFalse(testActor.hasUnresolvedEvents());
    }

    public void testUnavailableEvents() {
        setUp("TestEvent007.xml");
        // TODO this might need to be more intelligent in future
        for (int i = 0; i < 10; i++) {
            world.generateEventsForActors();
        }
        assertTrue(testActor.hasUnresolvedEvents());
        List<Event> events = testActor.getEvents();
        for (Event event : events) {
            assertEquals("TEST.EVENT_003", event.getId());
            assertEquals("Third Test Event", event.getName());
            assertEquals("This test event includes options", event.getDescription());
            assertFalse(event.getOptions().isEmpty());
            assertEquals("Option 1", event.getOptions().get(0).getDisplayText());
            assertEquals("Option 2", event.getOptions().get(1).getDisplayText());
        }
    }

    public void testManuallyAssigningEvent() {
        setUp("TestEvent007.xml");
        assertTrue(testActor.addEvent(world.getEvent("TEST.EVENT_003")));
        assertTrue(testActor.hasUnresolvedEvents());
        Event event = testActor.getEvents().get(0);
        assertEquals("TEST.EVENT_003", event.getId());
        assertEquals("Third Test Event", event.getName());
        assertEquals("This test event includes options", event.getDescription());
        assertFalse(event.getOptions().isEmpty());
        assertEquals("Option 1", event.getOptions().get(0).getDisplayText());
        assertEquals("Option 2", event.getOptions().get(1).getDisplayText());
    }

    public void testAssigningEventViaResult() {
        setUp("TestEvent007.xml");
        assertTrue(testActor.addEvent(world.getEvent("TEST.EVENT_003")));
        assertTrue(testActor.hasUnresolvedEvents());
        Event event = testActor.getEvents().get(0);
        assertNotNull(event.getOptions().get(0).getResults().get(0));
        testActor.handleEvent(event, event.getOptions().get(0), true);
        assertTrue(testActor.hasUnresolvedEvents());
        event = testActor.getEvents().get(0);
        assertEquals("TEST.EVENT_004", event.getId());
        assertEquals("Unavailable Event", event.getName());
        assertEquals("You can't even get this event without haxx!", event.getDescription());
        assertFalse(event.getOptions().isEmpty());
        assertEquals("Option 1", event.getOptions().get(0).getDisplayText());
        assertEquals("Option 2", event.getOptions().get(1).getDisplayText());
    }

    public void testEventSpam() {
        setUp("TestEvent007.xml");
        for (int i = 0; i < 20; i++) {
            world.addActor(new Actor());
        }
        world.generateEventsForActors();
        world.resolveActiveEvents();
        for (int i = 0; i < 20; i++) {
            world.generateEventsForActors();
        }
        world.resolveActiveEvents();
        assertFalse(testActor.hasUnresolvedEvents());
    }

}
