package org.willyams.dynasty.fiction.engine.events;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import org.willyams.dynasty.fiction.engine.world.Actor;

@XmlType(namespace = "http://willyams.com/event")
@XmlAccessorType(XmlAccessType.NONE)
public class Event {

    @XmlElement(required = true, nillable = false)
    private String id;

    @XmlElement(name = "available", required = false, defaultValue = "true", type = Boolean.class)
    private final Boolean available;

    @XmlElement
    private String name;

    @XmlElement
    private String description;

    @XmlElement(name = "option", type = Option.class)
    @XmlElementWrapper(name = "options")
    private List<Option> options;

    public Event() {
        options = new ArrayList<Option>();
        available = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        if (id == null || id.equals("")) {
            throw new RuntimeException("ID for Event " + this + " cannot be null");
        }
        String parentId = ((EventCollection) parent).getId();
        if (parentId != null) {
            id = parentId + '.' + id;
        }
        if (options.isEmpty()) {
            options.add(Option.DEFAULT_OPTION);
        }
    }

    public void chooseOption(Actor initiator, Option option) {
        if (option != null && this.options.contains(option)) {
            option.apply(initiator);
        } else {
            throw new IllegalArgumentException("Attempted to choose an invalid option " + option == null ? "null" : option.simpleString() + " for event " + this.getId());
        }
    }

    public boolean isAvailable() {
        return available;
    }

}
