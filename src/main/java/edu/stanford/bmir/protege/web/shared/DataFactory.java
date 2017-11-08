package edu.stanford.bmir.protege.web.shared;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import edu.stanford.bmir.protege.web.shared.entity.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryInternalsImplNoCache;

import java.util.Date;
import java.util.Optional;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 20/11/2012
 */
public class DataFactory {

    public static final String FRESH_ENTITY_SCHEME = "wptmp";

    private static OWLDataFactory dataFactory = new OWLDataFactoryImpl(new OWLDataFactoryInternalsImplNoCache(false));

    public static OWLDataFactory get() {
        return dataFactory;
    }

    public static OWLClass getOWLThing() {
        return dataFactory.getOWLThing();
    }

    public static IRI getIRI(String iri) {
        return IRI.create(iri);
    }

    public static OWLClass getOWLClass(String iri) {
        return getOWLClass(getIRI(iri));
    }

    public static OWLClass getOWLClass(IRI iri) {
        return dataFactory.getOWLClass(iri);
    }

    public static OWLObjectProperty getOWLObjectProperty(String iri) {
        return getOWLObjectProperty(getIRI(iri));
    }

    public static OWLObjectProperty getOWLObjectProperty(IRI iri) {
        return dataFactory.getOWLObjectProperty(iri);
    }

    public static OWLDataProperty getOWLDataProperty(IRI iri) {
        return dataFactory.getOWLDataProperty(iri);
    }

    public static OWLDataProperty getOWLDataProperty(String iri) {
        return dataFactory.getOWLDataProperty(getIRI(iri));
    }

    public static OWLAnnotationProperty getOWLAnnotationProperty(IRI iri) {
        return dataFactory.getOWLAnnotationProperty(iri);
    }

    public static OWLAnnotationProperty getOWLAnnotationProperty(String iri) {
        return dataFactory.getOWLAnnotationProperty(getIRI(iri));
    }


    public static OWLNamedIndividual getOWLNamedIndividual(String iri) {
        return getOWLNamedIndividual(getIRI(iri));
    }

    public static OWLNamedIndividual getOWLNamedIndividual(IRI iri) {
        return dataFactory.getOWLNamedIndividual(iri);
    }

    public static OWLDatatype getOWLDatatype(IRI iri) {
        return dataFactory.getOWLDatatype(iri);
    }

    public static OWLDatatype getOWLDatatype(String iri) {
        return dataFactory.getOWLDatatype(getIRI(iri));
    }

    public static <T extends OWLEntity> T getOWLEntity(EntityType<T> entityType, String iri) {
        return getOWLEntity(entityType, getIRI(iri));
    }

    public static IRI getFreshOWLEntityIRI(String shortName) {
        String iri = getFreshIRIString(shortName);
        return IRI.create(iri);
    }

    public static <T extends OWLEntity> T getFreshOWLEntity(EntityType<T> entityType, String shortName) {
        String iri = getFreshIRIString(shortName);
        return getOWLEntity(entityType, iri);
    }

    private static String getFreshIRIString(String shortName) {
        return FRESH_ENTITY_SCHEME + ":entity#" + shortName;
    }

    public static boolean isFreshEntity(OWLEntity entity) {
        IRI iri = entity.getIRI();
        String scheme = iri.getScheme();
        return scheme != null && FRESH_ENTITY_SCHEME.equalsIgnoreCase(scheme);
    }

    public static String getFreshEntityShortName(OWLEntity entity) {
        String iri = entity.getIRI().toString();
        if(!iri.startsWith(FRESH_ENTITY_SCHEME)) {
            throw new RuntimeException(entity.toStringID() + " is not a fresh entity");
        }
        int firstHashIndex = iri.indexOf('#');
        if(firstHashIndex == -1) {
            throw new RuntimeException("Malformed fresh entity: Could not find #");
        }
        return iri.substring(firstHashIndex + 1);
    }




    public static <T extends OWLEntity> T getOWLEntity(EntityType<T> entityType, IRI iri) {
        return dataFactory.getOWLEntity(entityType, iri);
    }

    public static OWLEntityData getOWLEntityData(OWLEntity entity, final String browserText) {
        return entity.accept(new OWLEntityVisitorEx<OWLEntityData>() {
            @Override
            public OWLEntityData visit(OWLClass owlClass) {
                return new OWLClassData(owlClass, browserText);
            }

            @Override
            public OWLEntityData visit(OWLObjectProperty property) {
                return new OWLObjectPropertyData(property, browserText);
            }

            @Override
            public OWLEntityData visit(OWLDataProperty property) {
                return new OWLDataPropertyData(property, browserText);
            }

            @Override
            public OWLEntityData visit(OWLNamedIndividual individual) {
                return new OWLNamedIndividualData(individual, browserText);
            }

            @Override
            public OWLEntityData visit(OWLDatatype datatype) {
                return new OWLDatatypeData(datatype, browserText);
            }

            @Override
            public OWLEntityData visit(OWLAnnotationProperty property) {
                return new OWLAnnotationPropertyData(property, browserText);
            }
        });
    }

    public static OWLLiteral getOWLLiteral(int value) {
        return dataFactory.getOWLLiteral(value);
    }

    public static OWLLiteral getOWLLiteral(boolean value) {
        return dataFactory.getOWLLiteral(value);
    }

    public static OWLLiteral getOWLLiteral(double value) {
        return dataFactory.getOWLLiteral(value);
    }


    private static OWLLiteral getOWLLiteral(Date date) {
        return DataFactory.getOWLLiteral(date.toString(), DataFactory.getXSDDateTime());
    }

    public static OWLLiteral getOWLLiteral(String value) {
        return dataFactory.getOWLLiteral(value);
    }

    public static OWLLiteral getOWLLiteral(String value, OWLDatatype datatype) {
        return dataFactory.getOWLLiteral(value, datatype);
    }

    public static OWLLiteral getDateTime(String value) {
        return dataFactory.getOWLLiteral(value, getXSDDateTime());
    }


    public static OWLLiteral getOWLLiteral(String value, String lang) {
        return dataFactory.getOWLLiteral(value, lang);
    }

    public static OWLDatatype getXSDInteger() {
        return dataFactory.getIntegerOWLDatatype();
    }

    public static OWLDatatype getXSDString() {
        return dataFactory.getOWLDatatype(XSDVocabulary.STRING.getIRI());
    }

    public static OWLDatatype getXSDDouble() {
        return dataFactory.getOWLDatatype(XSDVocabulary.DOUBLE.getIRI());
    }

    public static OWLDatatype getXSDDecimal() {
        return dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI());
    }

    public static OWLDatatype getXSDDateTime() {
        return dataFactory.getOWLDatatype(XSDVocabulary.DATE_TIME.getIRI());
    }


    /**
     * Parses a string and optional language string into an {@link OWLLiteral}.
     * @param lexicalValue The lecical value to parse.  Not {@code null}.
     * @param language The optional language.  Not {@code null}.
     * @return The parsed literal.  If {@code language} is present then the literal will be a plain literal with the
     * language tag.  If the lexical value corresponds to true or false then the literal will be typed as an xsd:boolean.
     * If the lexical value can be parsed as a datetime then the literal will be typed with xsd:dateTime.  If the lexical
     * value can be parsed as an integer then the lexical value will be typed with xsd:integet.  If the lexical value
     * can be parsed as a double then the literal will be typed with xsd:double.  Finally, if none of the previous can
     * be accomplished then the returned value is a plain literal without a language tag.
     * @throws NullPointerException if {@code lexicalValue} is {@code null} or {@code language} is {@code null}.
     */
    public static OWLLiteral parseLiteral(String lexicalValue, Optional<String> language) {
        String lang = language.orElse("");
        if (!lang.isEmpty()) {
            return DataFactory.getOWLLiteral(lexicalValue, lang);
        }
        String trimmedContent = lexicalValue.trim();
        if ("true".equalsIgnoreCase(trimmedContent)) {
            return DataFactory.getOWLLiteral(true);
        }
        if ("false".equalsIgnoreCase(trimmedContent)) {
            return DataFactory.getOWLLiteral(false);
        }
        try {
            return parseDateTimeFormat(trimmedContent);
        }
        catch (IllegalArgumentException e) {
            String numberWithoutCommas = trimmedContent.replace(",", "");
            try {
                Integer value = Integer.parseInt(numberWithoutCommas);
                return DataFactory.getOWLLiteral(value.intValue());
            }
            catch (NumberFormatException e3) {
                try {
                    if(numberWithoutCommas.endsWith("f") || numberWithoutCommas.endsWith("F")) {
                        Float.parseFloat(numberWithoutCommas);
                        return DataFactory.getOWLLiteral(numberWithoutCommas, DataFactory.getOWLDatatype(OWL2Datatype.XSD_FLOAT.getIRI()));
                    }
                    else {
                        Double value = Double.parseDouble(numberWithoutCommas);
                        return DataFactory.getOWLLiteral(numberWithoutCommas, DataFactory.getXSDDecimal());
                    }
                } catch (NumberFormatException e1) {
                    return DataFactory.getOWLLiteral(trimmedContent, DataFactory.getXSDString());
                }
            }

        }
    }


    /**
     * Parses the specified lexical value into a datetime literal.  The parser will parse dateTime according to the
     * xsd:dateTime pattern.  If the time information is not present, the end of the day will automatically be assumed.
     * @param lexicalValue The lexical value to be parsed.  Not {@code null}.
     * @return The literal representing the specified datetime.
     * @throws IllegalArgumentException if the lexical value cannot be parsed into a date-time format.
     */
    public static OWLLiteral parseDateTimeFormat(final String lexicalValue) throws IllegalArgumentException {
        final String yearFrag = "-?(?:[1-9][0-9]{3,}|0[0-9]{3})";
        final String monthFrag = "-(?:0[1-9]|1[0-2])";
        final String dayFrag = "-(?:0[1-9]|[12][0-9]|3[01])";
        final String yearMonthDayFrag = "(" + yearFrag + monthFrag + dayFrag + ")"; // Group 1
        // Slight modification to make input easier
        final String timeFrag = "((?:T| )(?:(?:[01][0-9]|2[0-3]):(?:[0-5][0-9]):(?:[0-5][0-9])(?:\\.[0-9]+)?|(?:24:00:00(?:\\.0+)?)))?"; // Group 2
        final String timeZoneFrag = "(Z|(?:\\+|-)(?:(?:0[0-9]|1[0-3]):[0-5][0-9]|14:00))?"; // Group 3

        String pattern = "^" + yearMonthDayFrag + timeFrag + timeZoneFrag + "$";
        RegExp regExp = RegExp.compile(pattern);
        MatchResult matchResult = regExp.exec(lexicalValue);
        if(matchResult == null) {
            throw new IllegalArgumentException();
        }
        String matchedYearMonthDay = matchResult.getGroup(1);
        String matchedTime = matchResult.getGroup(2);
        String matchedTimeZone = matchResult.getGroup(3);

        String properLexicalValue = matchedYearMonthDay;
        if(matchedTime != null) {
            if (!matchedTime.startsWith("T")) {
                properLexicalValue += "T" + matchedTime.trim();
            }
            else {
                properLexicalValue += matchedTime;
            }
        }
        else {
            properLexicalValue += "T00:00:00";
        }
        if(matchedTimeZone != null) {
            properLexicalValue += matchedTimeZone;
        }
        return dataFactory.getOWLLiteral(properLexicalValue, OWL2Datatype.XSD_DATE_TIME);
    }

    private static boolean isNow(String trimmedContent) {
        return "today".equalsIgnoreCase(trimmedContent) || "now".equalsIgnoreCase(trimmedContent);
    }

}
