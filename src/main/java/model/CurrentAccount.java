package model;

/**
 * Created by mariano on 29/03/18.
 */
public class CurrentAccount {


    private String id;
    private Integer credits = 0;


    public CurrentAccount(){}


    public CurrentAccount(String id){
        this.id = id;
    }



    public String getId() {
        return id;
    }


    public Integer getCredits() {
        return credits;
    }



    public  void addCredit(Integer credit){
        this.credits += credit;
    }


    public void subtractCredit(Integer credit){
        this.credits -= credit;
    }



    // probando el heroku con travis

}
