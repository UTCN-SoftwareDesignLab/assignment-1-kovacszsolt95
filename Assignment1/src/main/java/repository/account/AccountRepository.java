package repository.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

/**
 * Created by Alex on 07/03/2017.
 */
public interface AccountRepository {

    List<Account> findAll();

    List<Account> findByOwnerID(Long client_id);

    boolean updateBalance(Long accountID, Long newBalance);

    boolean removeByID(Long id);

    Account findById(Long id) throws EntityNotFoundException;

    boolean save(Account account);

    void removeAll();

}
