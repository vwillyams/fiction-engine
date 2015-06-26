package org.willyams.dynasty.fiction.engine.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.willyams.dynasty.fiction.engine.events.Event;
import org.willyams.dynasty.fiction.engine.events.Option;

public class Actor {

    private List<Event> events;
    private static final Logger logger = LoggerFactory.getLogger(Actor.class);

    public Actor() {
        events = new ArrayList<Event>();
    }

    public boolean addEvent(Event addEvent) {
        if (addEvent != null) {
            return events.add(addEvent);
        } else {
            logger.error("Attempted to reference null event!");
            return false;
        }
    }

    public void handleAllEvents() {
        while (!events.isEmpty()) {
            List<Event> temp = new ArrayList<Event>(events);
            Spliterator<Event> it = temp.spliterator();
            EventConsumer consume = new EventConsumer(this);
            it.forEachRemaining(consume);
        }
    }

    public void handleEvent(Event event, Option option, boolean remove) {
        try {
            event.chooseOption(this, option);
        } catch (Exception e) {
            logger.info(e.toString(), e);
        } finally {
            if (remove) {
                events.remove(event);
            }
        }
    }

    public boolean hasUnresolvedEvents() {
        return !events.isEmpty();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void clearEvents() {
        events.clear();
    }

}
