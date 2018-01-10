package com.example.kamil.restaurant.Models;

/**
 * Created by dm on 10.01.18.
 */

public class Osoba {



    private String login;
    private Integer znizka;
    private String email;
    private String haslo;

    private Integer id;

    public Osoba() {

    }

    public Osoba(Integer id, String login, Integer znizka, String email, String haslo) {
        this.id = id;
        this.login = login;
        this.znizka = znizka;
        this.email = email;
        this.haslo = haslo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getlogin() {
        return login;
    }

    public void setlogin(String login) {
        this.login = login;
    }

    public Integer getznizka() {
        return znizka;
    }

    public void setznizka(Integer znizka) {
        this.znizka = znizka;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String gethaslo() {
        return haslo;
    }

    public void sethaslo(String haslo) {
        this.haslo = haslo;
    }
}
        

