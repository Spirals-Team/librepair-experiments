package pl.put.poznan.transformer.logic.transform;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LongToShortTransformTest {
    private Transform transform;
    private LongToShortTransform longToShortTransform;

    @Before
    public void setUp() {
        this.transform = new FinalTransform();
        this.longToShortTransform = new LongToShortTransform(transform);
    }

/* NP */
//Only shortcut
    @Test
    public void testOnlynp(){
        String result = longToShortTransform.apply("na przykład");
        assertTrue(result.equals("np."));
    }
    @Test
    public void testOnlyNp(){
        String result = longToShortTransform.apply("Na przykład");
        assertTrue(result.equals("Np."));
    }

//One in sentence
    @Test
    public void testNpSentence(){
        String result = longToShortTransform.apply("Pieczywo to Na przykład chleb i bułki");
        assertTrue(result.equals("Pieczywo to Np. chleb i bułki"));
    }

    @Test
    public void testnpSentence(){
        String result = longToShortTransform.apply("Pieczywo to na przykład chleb i bułki");
        assertTrue(result.equals("Pieczywo to np. chleb i bułki"));
    }

//Sentence end
    @Test
    public void testNpSentenceEnd(){
        String result = longToShortTransform.apply("Pieczywo to chleb i bułki Na przykład .");
        assertTrue(result.equals("Pieczywo to chleb i bułki Np. ."));
    }

    @Test
    public void testnpSentenceEnd(){
        String result = longToShortTransform.apply("Pieczywo to chleb i bułki na przykład .");
        assertTrue(result.equals("Pieczywo to chleb i bułki np. ."));
    }

/* Min */
//Only shortcut

    @Test
    public void testOnlymin(){
        String result = longToShortTransform.apply("między innymi");
        assertTrue(result.equals("m.in."));
    }
    @Test
    public void testOnlyMin(){
        String result = longToShortTransform.apply("Między innymi");
        assertTrue(result.equals("M.in."));
    }
//One in sentence

    @Test
    public void testMinSentence(){
        String result = longToShortTransform.apply("Pieczywo to Między innymi chleb i bułki");
        assertTrue(result.equals("Pieczywo to M.in. chleb i bułki"));
    }

    @Test
    public void testminSentence(){
        String result = longToShortTransform.apply("Pieczywo to między innymi chleb i bułki");
        assertTrue(result.equals("Pieczywo to m.in. chleb i bułki"));
    }

//Sentence end
    @Test
    public void testMinSentenceEnd(){
        String result = longToShortTransform.apply("Pieczywo to chleb i bułki Między innymi.");
        assertTrue(result.equals("Pieczywo to chleb i bułki M.in.."));
    }

    @Test
    public void testminSentenceEnd(){
        String result = longToShortTransform.apply("Pieczywo to chleb i bułki między innymi.");
        assertTrue(result.equals("Pieczywo to chleb i bułki m.in.."));
    }


/* ITP */
//Only shortcut
    @Test
    public void testOnlyitp(){
        String result = longToShortTransform.apply("i tym podobne");
        assertTrue(result.equals("itp."));
    }
    @Test
    public void testOnlyItp(){
        String result = longToShortTransform.apply("I tym podobne");
        assertTrue(result.equals("Itp."));
    }
//One in sentence
    @Test
    public void testItpSentence(){
        String result = longToShortTransform.apply("Pieczywo to chleb, bułki I tym podobne produkty.");
        assertTrue(result.equals("Pieczywo to chleb, bułki Itp. produkty."));
    }

    @Test
    public void testitpSentence(){
        String result = longToShortTransform.apply("Pieczywo to chleb, bułki i tym podobne produkty.");
        assertTrue(result.equals("Pieczywo to chleb, bułki itp. produkty."));
    }

//Sentence end
    @Test
    public void testItpSentenceEnd(){
        String result = longToShortTransform.apply("Pieczywo to chleb, bułki I tym podobne .");
        assertTrue(result.equals("Pieczywo to chleb, bułki Itp. ."));
    }

    @Test
    public void testitpSentenceEnd(){
        String result = longToShortTransform.apply("Pieczywo to chleb, bułki i tym podobne .");
        assertTrue(result.equals("Pieczywo to chleb, bułki itp. ."));
    }

//All in one sentence
    @Test
    public void testAllInOneSentence(){
        String result = longToShortTransform.apply("Pieczywo to między innymi na przykład chleb, bułki i tym podobne .");
        assertTrue(result.equals("Pieczywo to m.in. np. chleb, bułki itp. ."));
    }
}