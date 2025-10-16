package com.example.springboot.resources;

public class DBUser{ 
    private Integer id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;


    public DBUser(Integer id, String firstname, String lastname, String username, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public void setId(int newId){
        id = newId;
    }
    public int getId(){
        return id;
    }

    public void setFirstname(String newFirstname){
        firstname = newFirstname;
    }
    public String getFirstname(){
        return firstname;
    }

    public void setLastname(String newLastname) {
        lastname = newLastname;
    }
    public String getLastname() {
        return lastname;
    }

    public void setUsername(String newUsername) {
        username = newUsername;
    }
    public String getUsername() {
        return username;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }
    public String getPassword() {
        return password;
    }

    public String toString(){
        return (id.toString()+firstname+lastname+username+password);
    }
}