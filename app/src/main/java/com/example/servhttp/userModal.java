package com.example.servhttp;

public class userModal {
    private int id;
    private String email,firstname,lastname,avatar;

    public userModal(int id, String email, String firstname, String lastname, String avatar) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
