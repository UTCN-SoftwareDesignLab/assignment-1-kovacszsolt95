package repository.client;

import model.Client;
import model.validation.Notification;
import repository.Cache;
import repository.EntityNotFoundException;

import java.util.List;

/**
 * Created by Alex on 07/03/2017.
 */
public class ClientRepositoryCacheDecorator extends ClientRepositoryDecorator {

    private Cache<Client> cache;

    public ClientRepositoryCacheDecorator(ClientRepository accountRepository, Cache<Client> cache) {
        super(accountRepository);
        this.cache = cache;
    }

    @Override
    public List<Client> findAll() {
        if (cache.hasResult()) {
            return cache.load();
        }
        List<Client> clients = decoratedRepository.findAll();
        cache.save(clients);
        return clients;
    }

    @Override
    public Notification<Client> findById(Long id) {
        return decoratedRepository.findById(id);
    }

    @Override
    public Boolean isCNPFree(String CNP) {
        return decoratedRepository.isCNPFree(CNP);
    }

    @Override
    public boolean save(Client client) {
        cache.invalidateCache();
        return decoratedRepository.save(client);
    }

    @Override
    public boolean updateClient(Client client) {
        return decoratedRepository.updateClient(client);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }

    @Override
    public boolean removeByID(Long id) {
        return decoratedRepository.removeByID(id);
    }

    @Override
    public Long findIDbyCNP(String CNP) throws EntityNotFoundException {
        return decoratedRepository.findIDbyCNP(CNP);
    }
}
