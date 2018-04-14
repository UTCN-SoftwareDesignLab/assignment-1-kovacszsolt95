package service.account;


import model.Account;
import model.validation.Notification;

import java.util.List;

public interface AccountService {
    List<Long> findAllIDs();

    List<Long> findAllIDByOwnerId(Long ownerID);

    Notification<Boolean> createAccount(Long ownerID, String type, String balance);

    Notification<Boolean> transfer(Long fromAccountID, Long targetAccountID, String amount);

    Notification<Boolean> addBalanceTo(Long accountID, String amount);

    Notification<Account> findById(Long id);

    Notification<Boolean> deleteByID(Long ID);
}
