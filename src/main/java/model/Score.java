package model;

/**
 * Created by mariano on 03/04/18.
 */
public class Score {

    private Long id;
    private Integer value;
    private String comment;


    public Score(){}


    public Score(Integer value, String comment){
        this.value = value;
        this.setComment(comment);
    }


    public Integer getValue() {
        return value;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
