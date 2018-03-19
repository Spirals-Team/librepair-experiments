package pl.kolejarz.domain;

public class Player
{
    private int id;
    private String firstName;
    private String nickName;
    private String age;
    private String team;
    private String game;

    public Player(){}

    public Player(int id,String firstName, String nickName)
    {
        this.id = id;
        this.firstName = firstName;
        this.nickName = nickName;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getFIrstName()
    {
        return firstName;
    }


    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getNickName()
    {
        return nickName;
    }
}