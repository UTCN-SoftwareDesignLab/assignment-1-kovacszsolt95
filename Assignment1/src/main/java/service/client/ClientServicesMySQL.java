package service.client;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.client.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientServicesMySQL implements ClientServices {
    ClientRepository clientRepository;

    public ClientServicesMySQL(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Long> findAllIDs() {
        List<Long> allID = new ArrayList<>();
        List<Client> allClients = clientRepository.findAll();
        for (Client client : allClients) {
            allID.add(client.getId());
        }
        return allID;
    }


    @Override
    public Notification<Boolean> createClient(String name, String CNP, String Adress) {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.setName(name);
        clientBuilder.setPersonalNumericCode(CNP);
        clientBuilder.setAdress(Adress);
        Client client = clientBuilder.build();
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientRegisterNotification = new Notification<>();


        if (!clientRepository.isCNPFree(client.getPersonalNumericCode())) {
            clientRegisterNotification.addError("A Client with this CNP already exists");
            clientRegisterNotification.setResult(Boolean.FALSE);
            return clientRegisterNotification;
        } else {
            if (!clientValid) {
                clientValidator.getErrors().forEach(clientRegisterNotification::addError);
                clientRegisterNotification.setResult(Boolean.FALSE);
                return clientRegisterNotification;
            } else {
                clientRegisterNotification.setResult(clientRepository.save(client));
                return clientRegisterNotification;
            }
        }

    }

    @Override
    public Notification<Boolean> update(Long ID, String name, String CNP, String Adress) {

        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.setName(name);
        clientBuilder.setPersonalNumericCode(CNP);
        clientBuilder.setAdress(Adress);
        clientBuilder.setId(ID);
        Client client = clientBuilder.build();
        ClientValidator clientValidator = new ClientValidator(client);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientUpdateNotification = new Notification<>();

        if (!clientValid) {
            clientValidator.getErrors().forEach(clientUpdateNotification::addError);
            clientUpdateNotification.setResult(Boolean.FALSE);
            return clientUpdateNotification;
        } else {
            clientUpdateNotification.setResult(clientRepository.updateClient(client));
            return clientUpdateNotification;
        }
    }

    @Override
    public Notification<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Notification<Boolean> deleteByID(Long ID) {
        Notification<Boolean> deleteByIDNotification = new Notification();
        if (clientRepository.removeByID(ID)) {
            deleteByIDNotification.setResult(Boolean.TRUE);
            return deleteByIDNotification;
        } else {
            deleteByIDNotification.setResult(Boolean.FALSE);
            deleteByIDNotification.addError("Could not find the ID to be Deleted");
            return deleteByIDNotification;
        }
    }
}
