package model;

public class Review {

    private Integer score;
    private String comment;
    private User evaluatedUser;
    //private User

    public Review(Integer score, String comment, User evaluatedUser){
        this.score = score;
        this.comment = comment;
        this.evaluatedUser = evaluatedUser;
    }
}
