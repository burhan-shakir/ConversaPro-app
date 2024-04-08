package com.example.conversapro.ui;
// Model for a User with all attribute getters and setters (Entity class)
public class User {
    String name;
    String email;
    String password;
    String description;

    public User(String name, String email, String password, String description){
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
    }

    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getDescription(){
        return description;
    }
    public String getPassword(){
        return password;
    }

}
