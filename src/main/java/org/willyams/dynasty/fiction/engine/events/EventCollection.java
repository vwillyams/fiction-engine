package org.willyams.dynasty.fiction.engine.events;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "events", namespace = "http://willyams.com/event")
@XmlType(name = "events", namespace = "http://willyams.com/event")
@XmlAccessorType(XmlAccessType.NONE)
public class EventCollection {

    @XmlElement(name = "id")
    private String collectionId;

    @XmlElement(name = "event")
    private final Set<Event> allEvents;

    private final Set<Event> availableEvents;

    public EventCollection() {
        allEvents = new HashSet<Event>();
        availableEvents = new HashSet<Event>();
    }

    public int size() {
        return allEvents.size();
    }

    public boolean isEmpty() {
        return allEvents.isEmpty();
    }

    public boolean contains(Event e) {
        return allEvents.contains(e);
    }

    public Iterator<Event> iterator() {
        return allEvents.iterator();
    }

    public boolean add(Event e) {
        return allEvents.add(e);
    }

    public boolean remove(Event e) {
        return allEvents.remove(e);
    }

    public boolean containsAll(Collection<Event> c) {
        return allEvents.containsAll(c);
    }

    public boolean addAll(Collection<? extends Event> c) {
        return allEvents.addAll(c);
    }

    public boolean removeAll(Collection<Event> c) {
        return allEvents.removeAll(c);
    }

    public boolean retainAll(Collection<Event> c) {
        return allEvents.retainAll(c);
    }

    /**
     * Please please please don't use this.
     *
     * @param index
     * @return
     */
    public Event get(int index) {
        return (Event) allEvents.toArray()[index];
    }

    public String getId() {
        return collectionId;
    }

    public void setId(String collectionId) {
        this.collectionId = collectionId;
    }

    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        for (Event event : allEvents) {
            if (event.isAvailable()) {
                availableEvents.add(event);
            }
        }
    }

    public Set<Event> getAvailableEvents() {
        return availableEvents;
    }

    public Event getEvent(String searchId) {
        for (Event event : allEvents) {
            if (event.getId().equals(searchId)) {
                return event;
            }
        }
        return null;
    }
}
