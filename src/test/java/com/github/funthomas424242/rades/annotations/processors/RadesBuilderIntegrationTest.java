package com.github.funthomas424242.rades.annotations.processors;

import com.github.funthomas424242.domain.Abteilung;
import com.github.funthomas424242.domain.AbteilungBuilder;
import com.github.funthomas424242.domain.Auto;
import com.github.funthomas424242.domain.CarBuilder;
import com.github.funthomas424242.domain.Familie;
import com.github.funthomas424242.domain.FamilieBuilder;
import com.github.funthomas424242.domain.Firma;
import com.github.funthomas424242.domain.FirmaAGErbauer;
import com.github.funthomas424242.domain.Person;
import com.github.funthomas424242.domain.PersonBuilder;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RadesBuilderIntegrationTest {

    @BeforeAll
    public static void setUp() {
        // needed for Travis CI wich has en
        Locale.setDefault(new Locale("de", "DE"));
    }

    @Test
    @DisplayName("Alle Felder der Auto gültig befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testAutoAlleFelderBefuellt() {
        final Auto firma = new CarBuilder()
                .withHersteller("Opel")
                .withMotor("Viertakt Motor")
                .withTyp("Corsa")
                .build();
        assertNotNull(firma);
    }


    @Test
    @DisplayName("Alle Felder der Firma gültig befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testFirmaAlleFelderBefuellt() {
        final Firma firma = new FirmaAGErbauer()
                .withName("Musterfirma")
                .withBetriebeNr("AG-8788-S")
                .build();
        assertNotNull(firma);
    }

    @Test
    @DisplayName("Alle Felder von Abteilung gültig befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testAbteilungAlleFelderBefuellt() {
        final Abteilung abteilung = new AbteilungBuilder()
                .withName("Musterabteilung")
                .withAbteilungsNr("IT-8788")
                .build();
        assertNotNull(abteilung);
    }

    @Test
    @DisplayName("Alle Felder von Familie gültig befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testFamilieAlleFelderBefuellt() {
        final Familie familie = new FamilieBuilder()
                .build();
        assertNotNull(familie);
    }

    @Test
    @DisplayName("Alle Felder von Person gültig befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testPersonAlleFelderBefuellt() {
        final Person person = new PersonBuilder()
                .withName("Mustermann")
                .withVorname("Max")
                .withBirthday(LocalDate.of(1968, 12, 25))
                .withGroesse(175)
                .withLieblingsfarben((HashSet<Person.Farbe>) Sets.newHashSet(Person.Farbe.BLAU))
                .build();
        assertNotNull(person);
    }

    @Test
    @DisplayName("Pflichtfelder von Person nicht befüllt.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testPersonPflichtfeldFehler() {

        Throwable exception = assertThrows(ValidationException.class, () -> {
            new PersonBuilder().build();
        });
        assertEquals("Person is not valid:\nname: darf nicht \"null\" sein", exception.getLocalizedMessage());
    }

    @Test
    @DisplayName("Optionale Felder von Person später befüllen.")
    @Tags({@Tag("integration"), @Tag("builder")})
    public void testPersonOptionaleFelderSpaeterBefuellt() {
        final Person person1 = new PersonBuilder()
                .withName("Mustermann")
                .build();
        assertNotNull(person1);
        final Person person = new PersonBuilder(person1)
                .withVorname("Max")
                .withBirthday(LocalDate.of(1968, 12, 25))
                .withGroesse(175)
                .withLieblingsfarben((HashSet<Person.Farbe>) Sets.newHashSet(Person.Farbe.BLAU))
                .build();
        assertNotNull(person);
    }

}