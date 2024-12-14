package Models;

public class Exercice {

    private String name;
    private double time;

    private int id;

    public Exercice(String name, double time, int id) {
        this.name = name;
        this.time = time;
        this.id = id;
    }

    public String getName() {return name;}

    public int getId() {return id;}

    public double getTime() {
        return time;
    }

    @Override
    public String toString() {
        return name + " - " + time;
    }


}
