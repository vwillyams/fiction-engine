package org.willyams.dynasty.fiction.engine.events;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.willyams.dynasty.fiction.engine.world.Actor;
import org.willyams.dynasty.fiction.engine.world.WorldState;

@XmlType(namespace = "http://willyams.com/event")
@XmlAccessorType(XmlAccessType.NONE)
public class Result {

    @XmlElement(name = "applyEvent")
    private String applyEvent;

    public void apply(Actor initiator) {
        if (applyEvent != null && !applyEvent.equals("")) {
            Event event = WorldState.instance().getEvent(applyEvent);
            if (event != null) {
                assert (initiator.addEvent(event) == true);
            }
        }
    }
}
