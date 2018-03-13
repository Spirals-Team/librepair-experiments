package fr.iut.nantes.quizzMovie.entite;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @version 1.0
 * @see Quizz
 * @since 1.0
 */
public class QuizzTest {

    Quizz quizz;

    /**
     * @since 1.0
     */
    @Before
    public void initialize() {
        quizz = new Quizz("question", "answers");
    }

    /**
     * @throws Exception
     * @since 1.0
     */
    @Test
    public void getQuestion() throws Exception {
        assertEquals("question", quizz.getQuestion());

    }

    /**
     * @throws Exception
     * @since 1.0
     */
    @Test
    public void setQuestion() throws Exception {
        Quizz quizz2 = new Quizz("question", "answers");
        assertEquals("question", quizz2.getQuestion());
        quizz2.setQuestion("otherQuestion");
        assertEquals("otherQuestion", quizz2.getQuestion());
    }

    /**
     * @throws Exception
     * @since 1.0
     */
    @Test
    public void getAnswers() throws Exception {
        assertEquals("answers", quizz.getAnswers());
    }

    /**
     * @throws Exception
     * @since 1.0
     */
    @Test
    public void setAnswers() throws Exception {
        Quizz quizz2 = new Quizz("answers", "answers");
        assertEquals("answers", quizz2.getQuestion());
        quizz2.setQuestion("otherAnswers");
        assertEquals("otherAnswers", quizz2.getQuestion());
    }

}