package org.willyams.dynasty.fiction.engine.xml;

import org.willyams.dynasty.fiction.engine.events.EventCollection;

public interface EventFileLoader {
    
    public EventCollection loadEventCollection(String filename);
    
}
