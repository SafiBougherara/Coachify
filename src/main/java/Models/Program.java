package Models;

public class Program {

    private int num_program;
    private boolean status;
    private double time;
    private int client_id;
    private int id;

    public Program(int id, int num_program, boolean status, double time, int client_id) {

        this.id = id;
        this.num_program = num_program;
        this.status = status;
        this.time = time;
        this.client_id = client_id;
    }


    public int getId() {return id;}
    public int getNum_program() {return num_program;}

    public boolean getStatus() {return status;}

    public double gettime() {return time;}

    public int getClient_id() {return client_id;}


    @Override
    public String toString() {

        if (status) {
            return "n° " + num_program + " | " + time + " minutes" + " | " + "statut : Terminé";
        }else {
            return "n° " + num_program + " | " + time + " minutes" + " | " + "statut : à faire";
        }
    }

}
