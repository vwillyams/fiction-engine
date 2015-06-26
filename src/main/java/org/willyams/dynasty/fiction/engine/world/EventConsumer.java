package org.willyams.dynasty.fiction.engine.world;

import java.util.function.Consumer;
import org.willyams.dynasty.fiction.engine.events.Event;
import org.willyams.dynasty.fiction.engine.support.Randomizer;

public class EventConsumer implements Consumer<Event> {

    private final Actor actor;

    public EventConsumer(Actor actor) {
        this.actor = actor;
    }

    public void accept(Event event) {
        actor.handleEvent(event, Randomizer.instance().getListEntry(event.getOptions()), true);
    }

}
