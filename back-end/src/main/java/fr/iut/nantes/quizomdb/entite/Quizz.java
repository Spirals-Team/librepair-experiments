package fr.iut.nantes.quizomdb.entite;

/**
 * @version 1.0
 * @since 1.0
 */
public class Quizz {
    private String question;
    private String answers;

    /**
     * Class constructor
     *
     * @param question
     * @param answers
     */
    public Quizz(String question, String answers) {
        this.question = question;
        this.answers = answers;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter of question
     *
     * @param question, the new question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the answers
     */
    public String getAnswers() {
        return answers;
    }

    /**
     * Setter of answers
     *
     * @param answers, the answers
     */
    public void setAnswers(String answers) {
        this.answers = answers;
    }


}
