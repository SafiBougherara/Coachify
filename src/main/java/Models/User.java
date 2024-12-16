 package Models;

import java.time.LocalDate;

public class User {

    private String username;
    private String password;
    private int id;

    public User(int id, String username, String password) {

        this.username = username;
        this.password = password;
        this.id = id;
    }

    public String getUsername() {return this.username;}

    public String getPassword() {return this.password;}


    public int getId() {
        return this.id;
    }



    @Override
    public String toString() {
        return "Utilisateur : " + username;
    }

}
