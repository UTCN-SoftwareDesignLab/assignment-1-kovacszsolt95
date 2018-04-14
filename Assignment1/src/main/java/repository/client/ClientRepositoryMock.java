package repository.client;

import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 07/03/2017.
 */
public class ClientRepositoryMock implements ClientRepository {

    private List<Client> clients;

    public ClientRepositoryMock() {
        clients = new ArrayList<>();
    }

    public List<Client> findAll() {
        return clients;
    }

    public Notification<Client> findById(Long id) {
        Notification<Client> clientNotification = new Notification<>();
        List<Client> filteredAccounts = clients.parallelStream()
                .filter(it -> it.getId().equals(id))
                .collect(Collectors.toList());
        if (filteredAccounts.size() > 0) {
            clientNotification.setResult(clients.get(0));
            return clientNotification;
        }
        return clientNotification;
    }

    @Override
    public Boolean isCNPFree(String CNP) {
        return true;
    }

    @Override
    public boolean save(Client client) {
        return clients.add(client);
    }

    @Override
    public boolean updateClient(Client client) {
        removeByID(client.getId());
        return clients.add(client);
    }

    @Override
    public void removeAll() {
        clients.clear();
    }

    @Override
    public boolean removeByID(Long id) {
        return clients.remove(findById(id));
    }

    @Override
    public Long findIDbyCNP(String CNP) throws EntityNotFoundException {
        for (Client client : clients) {
            if (client.getPersonalNumericCode().equals(CNP)) {
                return client.getId();
            }
        }
        throw new EntityNotFoundException(null, "No client with this CNP was found");
    }
}
