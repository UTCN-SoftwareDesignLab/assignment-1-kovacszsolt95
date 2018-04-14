package model.builder;

import model.Account;

import java.util.Date;

public class AccountBuilder {
    private Account account;

    public AccountBuilder() {
        this.account = new Account();
    }

    public void setId(Long id) {
        this.account.setId(id);
    }

    public void setOwnerId(Long ownerId) {
        this.account.setOwnerID(ownerId);
    }

    public void setType(String type) {
        this.account.setType(type);
    }

    public void setBalance(long balance) {
        this.account.setBalance(balance);
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.account.setDateOfCreation(dateOfCreation);
    }

    public Account build() {
        return this.account;
    }
}
