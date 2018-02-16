package pl.sternik.ss.zadania.Sklep;

public abstract class Person {

    private int id;
    private String name;
    private static int counter;

    public Person() {
        id=counter;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract boolean getAuthorization();

}
