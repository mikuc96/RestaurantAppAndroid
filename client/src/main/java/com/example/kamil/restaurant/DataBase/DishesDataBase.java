package com.example.kamil.restaurant.DataBase;

import com.example.kamil.restaurant.R;
import com.google.firebase.database.FirebaseDatabase;


public class DishesDataBase {
    private static FirebaseDatabase db=FirebaseDatabase.getInstance();
    private String id;
    private String name;
    private String price;
    private String description;
    private int photo;

    public static final DishesDataBase[] dishes={
            new DishesDataBase("1","Zupa Pomidorowa","20", "Zupa na bazie pomidorów z dodatkiem kwaśniej śmietany", R.drawable.pomidorowa),
            new DishesDataBase("2","Zupa Ogórkowa","30", "Potrawa kuchni polskiej, zupa gotowana na wywarze mięsnym z dodatkiem startych na tarce ogórków kiszonych lub przecieru ogórkowego i ziemniaków",R.drawable.ogorkowa),
            new DishesDataBase("3","Rosół","10", "Zupa będąca wywarem mięsno-warzywnym. Sporządzana z drobiu lub wołowiny albo baraniny, na Górnym Śląsku z gołębi. Podawana z makaronem typu nitki czy wstążki lub z innymi kluskami.",R.drawable.schabowy),
            new DishesDataBase("4","Krokiety","15", "Tradycyjne naleśniki zawijane w ruloniki z nadzieniem mięsnym (najczęściej z mięsa pozostałego po rosole).",R.drawable.krokiety),
            new DishesDataBase("5","Rolada wieprzowa","20", "Rodzaj potrawy mięsnej. Przygotowuje się ją przez zawinięcie farszu w kawałek mięsa. Składniki farszu różnią się w zależności od regionu, jednak najczęściej stosuje się boczek, kiełbasę, ogórek kiszony, cebulę oraz czerstwą skórkę chleba. Przyprawy to głównie papryka, pieprz i musztarda.",R.drawable.pomidorowa),
            new DishesDataBase("6","Filet z dorsza","30", "Część ryby w postaci płatu mięsa, niezawierający ości, kości ani skóry. Bardzo często wykorzystywany w kuchniach świata.",R.drawable.ogorkowa),
            new DishesDataBase("7","Schabowy","10", " Kotlet panierowany ze schabu przypominający sznycel wiedeński. Jest to jedna z tradycyjnych i najbardziej popularnych potraw w kuchni polskiej. Historia polskich kotletów schabowych sięga XIX wieku.",R.drawable.schabowy),
            new DishesDataBase("8","Szarlotka","15", "Pochodzący z Francji wyrób cukierniczy, wynalazek przypisywany Marie-Antoine'owi Carême'owi, składający się z półkruchego lub kruchego ciasta oraz owoców",R.drawable.krokiety)};

    private DishesDataBase(String id,String name, String price, String description, int photo) {
        this.id=id;
        this.name=name;
        this.price=price;
        this.description=description;
        this.photo=photo;
    }

    public static void  sentToFirebase()
    {
        for(DishesDataBase i:dishes)
        {
            db.getReference().child("Menu").child(i.id).setValue(i);
        }
    }

    public String getName()
    {
        return name;
    }

    public String getId()
    {
        return id;
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

