//package com.github.funthomas424242.rades.annotations.processors;
//
//import com.github.funthomas424242.domain.Person;
//import com.github.funthomas424242.domain.PersonBuilder;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Tags;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//
//public class RadesBuilderIntegrationTest {
//
//    @Test
//    @DisplayName("Alle Felder gültig befüllen.")
//    @Tags({@Tag("integration"),@Tag("builder")})
//    public void testAlleFelderBefuellt(){
//        final Person person = new PersonBuilder()
//                .withVorname("Max")
//                .withName("Mustermann")
//                .build();
//        assertNotNull(person);
//    }
//
//}