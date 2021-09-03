package collection;

import java.time.LocalDateTime;

public class Organization{
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float  annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address officialAddress; //Поле может быть null

    public Organization(Long id, String name) {
        this.id = id;
        this.name = name;
        this.creationDate = LocalDateTime.now();
        this.coordinates = new Coordinates();
        this.officialAddress = new Address();
    }

    public int compareTo(Organization org){
        int result;
        result = this.type.compareTo(org.GetType());
        if ( result == 0 ) {
            result = this.name.compareTo(org.GetName());
        }
        return result;
    }

    public LocalDateTime GetCreationDate() {
        return this.creationDate;
    }

    public long GetID() {
        return this.id;
    }

    public void SetID(Long id) {
        this.id = id;
    }

    public String GetName() {
        return this.name;
    }

    public boolean SetName(String name) {
        boolean done = true;
        if (name == null) {
            System.out.println("Ошибка ввода данных: Значение имени не может быть null");
            done = false;
        }
        if (name.trim().length() == 0) {
            System.out.println("Ошибка ввода данных: Значение имени не может быть пустым");
            done = false;
        }
        if (done) this.name = name.trim();

        return done;
    }

    public Coordinates GetCoordinates() { return this.coordinates; }

    public void SetCoordinates(Coordinates coordinates ) {
        this.coordinates = coordinates;
    }

    public Address GetOfficialAddress() {return officialAddress;}

    public void SetOfficialAddress(Address address ) {
        this.officialAddress = address;
    }

    public Float GetAnnualTurnover() { return this.annualTurnover; }

    public boolean SetAnnualTurnover(String annualTurnover ) {
        boolean done = true;
        try {
            this.annualTurnover = Float.parseFloat(annualTurnover);
        }
        catch (Exception e) {
            System.out.println("Ошибка ввода данных: Некорректный ввод данных");
            done = false;
        }
        if (done) {
            if (this.annualTurnover <= 0) {
                System.out.println("Ошибка ввода данных: Значение поля должно быть больше 0");
                done = false;
            }
        }
        return done;
    }

    public OrganizationType GetType() { return this.type; }

    public boolean SetType(String type) {
        boolean done = true;
        int type_index;
        try{
           type_index = Integer.parseInt(type);
           switch (type_index){
               case 1:
                   this.type = OrganizationType.COMMERCIAL;
                   break;
               case 2:
                   this.type = OrganizationType.PUBLIC;
                   break;
               case 3:
                   this.type = OrganizationType.GOVERNMENT;
                   break;
               case 4:
                   this.type = OrganizationType.TRUST;
                   break;
               default:
                   System.out.println("Ошибка ввода данных: Введенный неверный индекс");
                   done = false;
           }

        }
        catch (Exception e1) {
            try {
                this.type = OrganizationType.valueOf(type);
            }
            catch (Exception e2) {
                System.out.println("Ошибка ввода данных: Введенный неверный тип организации");
                done = false;
            }
        }
        return done;
    }

    public void PrintInfo() {
        System.out.print("ID: " + this.GetID());
        System.out.print(", Name: " + this.GetName());
        System.out.print(", Coordinates: " + this.GetCoordinates().GetX() + ";" + this.GetCoordinates().GetY());
        System.out.print(", LocalDateTime: " + this.GetCreationDate());
        System.out.print(", AnnualTurnover:" + this.GetAnnualTurnover());
        System.out.print(", OrganizationType:" + this.GetType());
        System.out.print(", Address:" + this.GetOfficialAddress().GetZipCode());
        System.out.println();
    }


}
