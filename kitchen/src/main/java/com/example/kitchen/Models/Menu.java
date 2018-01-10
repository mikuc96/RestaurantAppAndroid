package com.example.kitchen.Models;

/**
 * Created by dm on 10.01.18.
 */

public class Menu {


    private String nazwa;
    private Integer cena;
    private Integer czasprzygotowania;
    private String opis;

    private Integer id;

    public Menu() {

    }

    public Menu(Integer id, String nazwa, Integer cena, Integer czasprzygotowania, String opis) {
        this.id = id;
        this.nazwa = nazwa;
        this.cena = cena;
        this.czasprzygotowania = czasprzygotowania;
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Integer getCzasprzygotowania() {
        return czasprzygotowania;
    }

    public void setCzasprzygotowania(Integer czasprzygotowania) {
        this.czasprzygotowania = czasprzygotowania;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
