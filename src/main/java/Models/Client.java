package Models;

import java.time.LocalDate;

public class Client {

    private String name;
    private String firstname;
    private String phone;
    private String adresse;
    private String email;
    private int id;
    private LocalDate birth_date;

    public Client(int id, String firstname, String name, String phone, String adresse, String email, LocalDate birth_date) {

        this.id = id;
        this.phone = phone;
        this.adresse = adresse;
        this.email = email;
        this.name = name;
        this.firstname = firstname;
        this.birth_date = birth_date;
    }

    public String getName() {return this.name;}

    public String getFirstname() {return this.firstname;}


    public int getId() {
        return this.id;
    }


    public String getPhone() {return this.phone;}

    public LocalDate getDate() {return this.birth_date;}



    public String getAdresse() {
        return this.adresse;
    }

    public String getEmail() {
        return this.email;
    }

    public LocalDate getBirth_date() {
        return this.birth_date;
    }






    @Override
    public String toString() {
        return firstname + "  " + name + " | " + phone + " | " + email;
    }

}
