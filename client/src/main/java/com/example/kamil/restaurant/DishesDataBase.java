package com.example.kamil.restaurant;

public class DishesDataBase {
    private String name;
    private String price;
    private String description;
    private int photo;

    public static final DishesDataBase[] dishes={
            new DishesDataBase("Zupa Pomidorowa","20zł", "Zupa na bazie pomidorów z dodatkiem kwaśniej śmietany",R.drawable.pomidorowa),
            new DishesDataBase("Zupa Ogórkowa","30zł", "descrition2",R.drawable.ogorkowa),
            new DishesDataBase("Schabowy","10zł", "descrition3",R.drawable.schabowy),
            new DishesDataBase("Krokiety","15zł", "descrition4",R.drawable.krokiety),
            new DishesDataBase("Zupa Pomidorowa","20zł", "Zupa na bazie pomidorów z dodatkiem kwaśniej śmietany",R.drawable.pomidorowa),
            new DishesDataBase("Zupa Ogórkowa","30zł", "descrition2",R.drawable.ogorkowa),
            new DishesDataBase("Schabowy","10zł", "descrition3",R.drawable.schabowy),
            new DishesDataBase("Krokiety","15zł", "descrition4",R.drawable.krokiety)};

    private DishesDataBase(String name, String price, String description, int photo) {
        this.name=name;
        this.price=price;
        this.description=description;
        this.photo=photo;
    }

    public String getName()
    {
        return name;
    }

    public String getPrice()
    {
        return price;
    }

    public int getImg(){return  photo;}

    public String getDescription()
    {
        return description;
    }

    public String toString()
    {
        return this.name;
    }
    }

