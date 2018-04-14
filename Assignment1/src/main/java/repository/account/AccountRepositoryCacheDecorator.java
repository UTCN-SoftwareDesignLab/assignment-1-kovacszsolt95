package repository.account;

import model.Account;
import repository.Cache;
import repository.EntityNotFoundException;

import java.util.List;

/**
 * Created by Alex on 07/03/2017.
 */
public class AccountRepositoryCacheDecorator extends AccountRepositoryDecorator {

    private Cache<Account> cache;

    public AccountRepositoryCacheDecorator(AccountRepository accountRepository, Cache<Account> cache) {
        super(accountRepository);
        this.cache = cache;
    }

    @Override
    public List<Account> findAll() {
        if (cache.hasResult()) {
            return cache.load();
        }
        List<Account> accounts = decoratedRepository.findAll();
        cache.save(accounts);
        return accounts;
    }

    @Override
    public List<Account> findByOwnerID(Long client_id) {
        return decoratedRepository.findByOwnerID(client_id);
    }

    @Override
    public boolean updateBalance(Long accountID, Long newBalance) {
        return decoratedRepository.updateBalance(accountID, newBalance);
    }

    @Override
    public boolean removeByID(Long id) {
        return decoratedRepository.removeByID(id);
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        return decoratedRepository.findById(id);
    }

    @Override
    public boolean save(Account account) {
        cache.invalidateCache();
        return decoratedRepository.save(account);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }
}
