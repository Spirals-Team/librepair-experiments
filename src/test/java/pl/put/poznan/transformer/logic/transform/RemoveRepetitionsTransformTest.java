package pl.put.poznan.transformer.logic.transform;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RemoveRepetitionsTransformTest {
    private Transform transform;
    private RemoveRepetitionsTransform removeRepetitionsTransform;

    @Before
    public void setUp() {
        this.transform = new FinalTransform();
        this.removeRepetitionsTransform = new RemoveRepetitionsTransform(transform);
    }
    @Test
    public void testTransformationRemoveRepetitionsOneWordWithoutRepetitions() {
        assertEquals("Badaboom", removeRepetitionsTransform.apply("Badaboom"));
    }

    @Test
    public void testTransformationRemoveRepetitionsTwoWordsWithoutRepetitions() {
        assertEquals("Lorem ipsum", removeRepetitionsTransform.apply("Lorem ipsum"));
    }


    @Test
    public void testTransformationRemoveRepetitionsTwoWordsWithRepetitions() {
        assertEquals("Lorem", removeRepetitionsTransform.apply("Lorem Lorem"));
    }

    @Test
    public void testTransformationRemoveRepetitionsThreeWordsWithRepetitions() {
        assertEquals("Lorem", removeRepetitionsTransform.apply("Lorem Lorem Lorem"));
    }

    @Test
    public void testTransformationRemoveRepetitionsThreeWordsWithRepetitionsMultipleSpaces() {
        assertEquals("Lorem", removeRepetitionsTransform.apply("Lorem   Lorem   Lorem"));
    }

    @Test
    public void testTransformationRemoveRepetitionsThreeWordsWithRepetitionsMultipleSpacesCombined() {
        assertEquals("Lorem", removeRepetitionsTransform.apply("Lorem Lorem   Lorem     Lorem"));
    }

    @Test
    public void testTransformationRemoveRepetitionsFourWordsWithoutRepetitions() {
        assertEquals("Lorem ipsum Lorem ipsum", removeRepetitionsTransform.apply("Lorem ipsum Lorem ipsum"));
    }

    @Test
    public void testTransformationRemoveRepetitionsFourWordsWithRepetitions() {
        assertEquals("Lorem", removeRepetitionsTransform.apply("Lorem Lorem Lorem Lorem"));
    }

    @Test
    public void testTransformationRemoveRepetitionsFourWordsWithRepetitionsCaseSensitive() {
        assertEquals("Lorem lorem Lorem lorem", removeRepetitionsTransform.apply("Lorem lorem Lorem lorem"));
    }

    @Test
    public void testTransformationRemoveRepetitionsMultipleWordsWithRepetitionsCaseSensitive() {
        assertEquals("I hate Java java Java jAvA jaVA jaVa", removeRepetitionsTransform.apply("I hate Java java Java Java Java jAvA jaVA jaVa"));
    }

    @Test
    public void testTransformationRemoveRepetitionsMultipleWordsWithoutRepetitionsCaseSensitive() {
        assertEquals("The worst nightmare programmer can read? 3.8 billion devices running JAVA!", removeRepetitionsTransform.apply("The worst nightmare programmer can read? 3.8 billion devices running JAVA!"));
    }

    @Test
    public void testTransformationRemoveRepetitionsMultipleWordsInSentenceWithRepetitionsCaseSensitive() {
        assertEquals("KNOCK knock! Race condition ! Who is there?", removeRepetitionsTransform.apply("KNOCK knock! Race condition condition condition ! Who is there?"));
    }
}