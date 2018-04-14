package model.builder;

import model.Client;

public class ClientBuilder {
    private Client client;

    public ClientBuilder() {
        this.client = new Client();
    }

    public void setName(String name) {
        this.client.setName(name);
    }

    public void setId(Long id) {
        this.client.setId(id);
    }

    public void setPersonalNumericCode(String personalNumericCode) {
        this.client.setPersonalNumericCode(personalNumericCode);
    }

    public void setAdress(String adress) {
        this.client.setAdress(adress);
    }

    public Client build() {
        return this.client;
    }
}
