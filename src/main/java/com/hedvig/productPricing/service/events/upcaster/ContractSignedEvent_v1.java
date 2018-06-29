package com.hedvig.productPricing.service.events.upcaster;

import com.hedvig.productPricing.service.events.ContractSignedEvent;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;
import org.dom4j.Element;

public class ContractSignedEvent_v1 extends SingleEventUpcaster {

    private static SimpleSerializedType targetType = new SimpleSerializedType(ContractSignedEvent.class.getTypeName(), null);


    @Override
    protected boolean canUpcast(IntermediateEventRepresentation intermediateEventRepresentation) {
        return intermediateEventRepresentation.getType().equals(targetType);
    }

    @Override
    protected IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateEventRepresentation) {
        return intermediateEventRepresentation.upcastPayload(
                new SimpleSerializedType(targetType.getName(), "1.0"),
                org.dom4j.Document.class,
                document -> {
                    final Element rootElement = document.getRootElement();

                    final Element state = rootElement.element("state");

                    rootElement
                            .remove(state);

                    return document;
                }
        );
    }
}
