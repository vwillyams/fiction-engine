package org.willyams.dynasty.fiction.engine.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.willyams.dynasty.fiction.engine.events.Event;
import org.willyams.dynasty.fiction.engine.events.EventCollection;
import org.willyams.dynasty.fiction.engine.support.Randomizer;

public class WorldState {

    private static WorldState instance;
    
    private final List<Actor> activeActors;
    private final Map<String, EventCollection> allEvents;

    public static WorldState instance() {
        if (instance == null) {
            instance = new WorldState();
        }
        return instance;
    }

    private WorldState() {
        activeActors = new ArrayList<Actor>();
        allEvents = new HashMap<String, EventCollection>();
    }

    public void generateEventsForActors() {
        for (Actor actor : activeActors) {
            actor.addEvent(generateEvent());
        }
    }

    public Event generateEvent() {
        EventCollection collection = Randomizer.instance().getCollectionEntry(allEvents.values());
        return Randomizer.instance().getEvent(collection);
    }

    public void addActor(Actor actor) {
        activeActors.add(actor);
    }

    public void addEvents(EventCollection collection) {
        if (allEvents.containsKey(collection.getId())) {
            throw new IllegalArgumentException("Tried to provide multiple event collections with the same namespace, this is not allowed!");
        }
        allEvents.put(collection.getId(), collection);
    }

    public void resolveActiveEvents() {
        for (Actor actor : activeActors) {
            actor.handleAllEvents();
        }
    }

    public Event getEvent(String eventId) {
        EventCollection searchCollection;
        if (eventId.contains(".")) {
            searchCollection = allEvents.get(eventId.substring(0, eventId.indexOf(".")));
        } else {
            searchCollection = allEvents.get("");
        }
        if (searchCollection != null) {
            return searchCollection.getEvent(eventId);
        }
        return null;
    }

    public void clearEvents() {
        allEvents.clear();
    }

    public void clearActors() {
        activeActors.clear();
    }

}
