package repository.client;

import model.Client;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

/**
 * Created by Alex on 07/03/2017.
 */
public interface ClientRepository {

    List<Client> findAll();

    Notification<Client> findById(Long id);

    Boolean isCNPFree(String CNP);

    boolean save(Client client);

    boolean updateClient(Client client);

    void removeAll();

    boolean removeByID(Long id);

    Long findIDbyCNP(String CNP) throws EntityNotFoundException;
}
