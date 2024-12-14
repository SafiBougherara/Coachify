package Models;

public class Exercice {

    private String name;
    private double time;

    private int repExercice;

    private int id;

    public Exercice(String name, double time, int id, int repExercice) {
        this.name = name;
        this.time = time;
        this.repExercice = repExercice;
        this.id = id;
    }

    public String getName() {return name;}

    public int getId() {return id;}

    public double getTime() {return time;}

    public int getRepExercice() {return repExercice;}

    @Override
    public String toString() {
        return name + " | " + time + " minutes" + " | " + repExercice + " reppetitions";
    }


}
