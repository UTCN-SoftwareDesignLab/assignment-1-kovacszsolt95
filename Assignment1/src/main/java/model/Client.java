package model;

public class Client {
    private String name;
    private Long id;
    private String personalNumericCode;
    private String adress;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonalNumericCode() {
        return personalNumericCode;
    }

    public void setPersonalNumericCode(String personalNumericCode) {
        this.personalNumericCode = personalNumericCode;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
