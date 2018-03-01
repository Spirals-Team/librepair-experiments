package fr.iut.nantes.quizomdb.entite;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by E155722N on 22/02/18.
 */
public class QuizzTest {
    Quizz quizz;

	 @Before
	 public void initialize() {
		 quizz = new Quizz("question", "answers");
	    }

    @Test
    public void getQuestion() throws Exception {
	     assertEquals("question", quizz.getQuestion());

    }

    @Test
    public void setQuestion() throws Exception {
        Quizz quizz2 = new Quizz("question", "answers");
        assertEquals("question", quizz2.getQuestion());
        quizz2.setQuestion("otherQuestion");
        assertEquals("otherQuestion", quizz2.getQuestion());
    }

    @Test
    public void getAnswers() throws Exception {
        assertEquals("answers", quizz.getAnswers());
    }

    @Test
    public void setAnswers() throws Exception {
        Quizz quizz2 = new Quizz("answers", "answers");
        assertEquals("answers", quizz2.getQuestion());
        quizz2.setQuestion("otherAnswers");
        assertEquals("otherAnswers", quizz2.getQuestion());
    }

}