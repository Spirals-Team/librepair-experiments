package org.dominokit.jacksonapt;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import org.dominokit.jacksonapt.exception.JsonDeserializationException;
import org.dominokit.jacksonapt.stream.JsonReader;

public interface JsonDeserializationContext extends JsonMappingContext {
    boolean isFailOnUnknownProperties();

    boolean isUnwrapRootValue();

    boolean isAcceptSingleValueAsArray();

    boolean isUseSafeEval();

    boolean isReadUnknownEnumValuesAsNull();

    boolean isUseBrowserTimezone();

    JsonReader newJsonReader(String input);

    JsonDeserializationException traceError(String message);

    JsonDeserializationException traceError(String message, JsonReader reader);

    RuntimeException traceError(RuntimeException cause);

    RuntimeException traceError(RuntimeException cause, JsonReader reader);

    void addObjectId(ObjectIdGenerator.IdKey id, Object instance);

    Object getObjectWithId(ObjectIdGenerator.IdKey id);

    JsonDeserializerParameters defaultParameters();
}
