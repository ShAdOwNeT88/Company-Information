package alterego.solutions.company_information;

public class Company {

    String Name,Country,Street,Tel,Cell,Description;

    public Company(String name, String country, String street, String tel, String cell, String description){
        this.Name = name;
        this.Country = country;
        this.Street = street;
        this.Tel = tel;
        this.Cell = cell;
        this.Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getCell() {
        return Cell;
    }

    public void setCell(String cell) {
        Cell = cell;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
