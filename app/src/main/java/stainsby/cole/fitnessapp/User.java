package stainsby.cole.fitnessapp;

public class User {

    // required information
    private String firstName, lastName, email, password;

    //--------------------------------------------
    // optional personal information
    //--------------------------------------------
    private int age;

    public User() {

    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
