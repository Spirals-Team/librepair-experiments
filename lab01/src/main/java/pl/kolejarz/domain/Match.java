package pl.kolejarz.domain;



public class Match
{
    private String nameTeam1;
    private String nameTeam2;
    private int scoreTeam1;
    private int scoreTeam2;
    private String gameName;


    public Match(String nameTeam1,String nameTeam2, int scoreTeam1, int scoreTeam2, String gameName) {
        this.nameTeam1 = nameTeam1;
        this.nameTeam2 = nameTeam2;
        this.scoreTeam1 = scoreTeam1;
        this.scoreTeam2 = scoreTeam2;
        this.gameName = gameName;    
    }
}