package org.willyams.dynasty.fiction.engine.events;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.willyams.dynasty.fiction.engine.world.Actor;

@XmlType(name = "option", namespace = "http://willyams.com/event")
@XmlAccessorType(XmlAccessType.NONE)
public class Option {

    public static final Option DEFAULT_OPTION = new Option("NO_OPTION", "Very well.");

    @XmlElement(name = "text")
    private String displayText;
    // Entirely optional, used only to aid debugging.
    @XmlAttribute
    private String id = "SIMPLE";

    @XmlElement(name = "result")
    private final List<Result> results = new ArrayList<Result>();

    public Option(String id, String text) {
        this.displayText = text;
        this.id = id;
    }

    public Option() {
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String text) {
        this.displayText = text;
    }

    public void apply(Actor initiator) {
        for (Result result : results) {
            if (result != null) {
                result.apply(initiator);
            }
        }
    }

    public String getId() {
        return id;
    }

    public String simpleString() {
        return displayText + " (" + id + ")";
    }

    public List<Result> getResults() {
        return results;
    }

}
