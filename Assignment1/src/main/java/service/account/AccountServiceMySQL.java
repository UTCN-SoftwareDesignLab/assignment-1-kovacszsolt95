package service.account;

import model.Account;
import model.builder.AccountBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountServiceMySQL implements AccountService {
    AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Long> findAllIDs() {
        List<Long> allID = new ArrayList<>();
        List<Account> allAccounts = accountRepository.findAll();
        for (Account client : allAccounts) {
            allID.add(client.getId());
        }
        return allID;
    }

    public List<Long> findAllIDByOwnerId(Long ownerID) {
        List<Long> ownedAccountsID = new ArrayList<>();
        for (Account account : accountRepository.findAll()) {
            if (account.getOwnerID().equals(ownerID)) {
                ownedAccountsID.add(account.getId());
            }
        }
        return ownedAccountsID;
    }

    @Override
    public Notification<Boolean> createAccount(Long ownerID, String type, String balanceString) {
        Notification<Boolean> accountRegisterNotification = new Notification<>();
        if (!isValidBalance(balanceString)) {
            accountRegisterNotification.addError("Invalid Balance");
            return accountRegisterNotification;
        }
        Long balance = Long.parseLong(balanceString);
        AccountBuilder accountBuilder = new AccountBuilder();
        accountBuilder.setOwnerId(ownerID);
        accountBuilder.setType(type);
        accountBuilder.setDateOfCreation(new Date());
        accountBuilder.setBalance(balance);
        Account account = accountBuilder.build();


        accountRegisterNotification.setResult(accountRepository.save(account));
        return accountRegisterNotification;
    }


    @Override
    public Notification<Boolean> transfer(Long fromAccountID, Long targetAccountID, String amountString) {
        Notification<Boolean> transferNotification = new Notification<>();
        if (!isValidBalance(amountString)) {
            transferNotification.addError("Invalid Amount");
            return transferNotification;
        }
        Long amount = Long.parseLong(amountString);
        Account from;
        Account to;
        try {
            from = accountRepository.findById(fromAccountID);
            to = accountRepository.findById(targetAccountID);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            transferNotification.addError("Could not find these Accounts");
            transferNotification.setResult(Boolean.FALSE);
            return transferNotification;
        }
        if (newBalance(-amount, from) >= 0) {
            transferNotification.setResult(accountRepository.updateBalance(from.getId(), newBalance(-amount, from)) &&
                    accountRepository.updateBalance(to.getId(), newBalance(amount, to)));
            return transferNotification;

        } else {
            transferNotification.addError("Insufficient Funds");
            transferNotification.setResult(Boolean.FALSE);
            return transferNotification;
        }
    }

    private long newBalance(Long amount, Account from) {
        return from.getBalance() + amount;
    }

    private boolean isValidBalance(String StringContainingLong) {
        try {
            Long amount = Long.parseLong(StringContainingLong);
            if (amount < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Notification<Boolean> addBalanceTo(Long accountID, String amountString) {

        Notification<Boolean> transferNotification = new Notification<>();

        if (!isValidBalance(amountString)) {
            transferNotification.addError("Invalid Amount");
            return transferNotification;
        }
        Long amount = Long.parseLong(amountString);
        Account targetAccount;
        try {
            targetAccount = accountRepository.findById(accountID);

        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            transferNotification.addError("Could not find these Accounts");
            transferNotification.setResult(Boolean.FALSE);
            return transferNotification;
        }
        transferNotification.setResult(accountRepository.updateBalance(targetAccount.getId(), newBalance(amount, targetAccount)));
        return transferNotification;
    }

    @Override
    public Notification<Account> findById(Long id) {
        Notification<Account> accountNotification = new Notification<>();
        try {
            Account found = accountRepository.findById(id);
            accountNotification.setResult(found);
            return accountNotification;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            accountNotification.addError("Could not find ID");
            return accountNotification;
        }
    }

    @Override
    public Notification<Boolean> deleteByID(Long ID) {
        Notification<Boolean> deleteByIDNotification = new Notification();
        if (accountRepository.removeByID(ID)) {
            deleteByIDNotification.setResult(Boolean.TRUE);
            return deleteByIDNotification;
        } else {
            deleteByIDNotification.setResult(Boolean.FALSE);
            deleteByIDNotification.addError("Could not find the ID to be Deleted");
            return deleteByIDNotification;
        }
    }
}
