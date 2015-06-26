package org.willyams.dynasty.fiction.engine.xml;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;

public class SimpleValidationEventHandler extends ValidationEventCollector {

    public JaxbValidationSeverity maxSeverity;

    public SimpleValidationEventHandler(JaxbValidationSeverity maxSeverity) {
        this.maxSeverity = maxSeverity;
    }

    @Override
    public boolean handleEvent(ValidationEvent event) {
        super.handleEvent(event);
        if (event.getSeverity() >= maxSeverity.ordinal()) {
            return false;
        }
        return true;
    }

}
