package fr.iut.nantes.quizzMovie.entite;

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
     * @param question , the actual question of the quizz
     * @param answers , the actual answers of the quizz
     * @since 1.0
     */
    public Quizz(String question, String answers) {
        this.question = question;
        this.answers = answers;
    }

    /**
     * @return the question
     * @since 1.0
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Setter of question
     *
     * @param question, the new question
     * @since 1.0
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the answers
     * @since 1.0
     */
    public String getAnswers() {
        return answers;
    }

    /**
     * Setter of answers
     *
     * @param answers, the answers
     * @since 1.0
     */
    public void setAnswers(String answers) {
        this.answers = answers;
    }


}
