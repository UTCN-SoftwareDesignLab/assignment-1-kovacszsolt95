package service.client;

import model.Client;
import model.validation.Notification;

import java.util.List;

public interface ClientServices {
    List<Long> findAllIDs();

    Notification<Boolean> createClient(String name, String CNP, String Adress);

    Notification<Boolean> update(Long ID, String name, String CNP, String Adress);

    Notification<Client> findById(Long id);

    Notification<Boolean> deleteByID(Long ID);
}
