package org.willyams.dynasty.fiction.engine.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.willyams.dynasty.fiction.engine.events.Event;
import org.willyams.dynasty.fiction.engine.events.EventCollection;

public class Randomizer {

    private final Random random;
    private static Randomizer instance;

    public static Randomizer instance() {
        if (instance == null) {
            instance = new Randomizer();
        }
        return instance;
    }

    private Randomizer() {
        random = new Random();
        random.setSeed(new Date().getTime());
    }

    /**
     * Gets a number between min and max, inclusive of those numbers.
     *
     * @param min inclusive
     * @param max inclusive
     * @return
     */
    public int getInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Used for array indexing, this calls straight through to random to get a max value 1 less than provided.
     *
     * @param arraySize this is a non inclusive maximum
     * @return
     */
    public int getIntForArray(int arraySize) {
        if (arraySize > 1) {
            return random.nextInt(arraySize - 1);
        } else {
            return 0;
        }
    }

    public <T> T getListEntry(List<T> list) {
        return list.get(getIntForArray(list.size()));
    }

    public <T> T getCollectionEntry(Collection<T> collection) {
        // TODO kind of hacky, revisit later
        return getListEntry(new ArrayList<T>(collection));
    }

    public Event getEvent(EventCollection collection) {
        Set<Event> availableEvents = collection.getAvailableEvents();
        return getCollectionEntry(availableEvents);
    }

    /**
     * Probably best practice skill check randomization, this rolls XdY dice.
     *
     * @param numDice
     * @param sides
     * @return
     */
    public int dice(int numDice, int sides) {
        int result = 0;
        for (int i = 0; i < numDice; i++) {
            result += getInt(1, sides);
        }
        return result;
    }
}
