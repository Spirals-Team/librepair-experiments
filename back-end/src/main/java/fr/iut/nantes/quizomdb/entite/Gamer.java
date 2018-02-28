package fr.iut.nantes.quizomdb.entite;

/**
 * @version 1.0
 * @since 1.0
 */
public class Gamer {
    private String login;
    private int goodAnswers;
    private int answers;


    /**
     * Class constructor
     *
     * @param login
     * @param goodAnswers
     * @param answers
     */
    public Gamer(String login, int goodAnswers, int answers) {
        this.login = login;
        this.goodAnswers = goodAnswers;
        this.answers = answers;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @return the number of good answers
     */
    public int getGoodAnswers() {
        return goodAnswers;
    }


    /**
     * Add 1 to number of good answers
     */
    public void incrementGoodAnswers() {
        this.goodAnswers += 1;
    }


    /**
     * @return the number of answers
     */
    public int getAnswers() {
        return answers;
    }


    /**
     * Add 1 to number of good answers
     */
    public void incrementAnswers() {
        this.answers += 1;
    }

    /**
     * Override of the method equals
     *
     * @param o
     * @return true of false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gamer)) return false;

        Gamer gamer = (Gamer) o;

        return login.equals(gamer.login);
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + goodAnswers;
        result = 31 * result + answers;
        return result;
    }
}
