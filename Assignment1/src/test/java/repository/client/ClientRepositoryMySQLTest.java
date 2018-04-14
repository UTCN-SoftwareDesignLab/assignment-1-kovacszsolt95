package repository.client;

import database.DBConnectionFactory;
import model.Book;
import model.Client;
import model.builder.BookBuilder;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;
import repository.EntityNotFoundException;
import repository.account.AccountRepositoryCacheDecorator;
import repository.account.AccountRepositoryMySQL;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex on 07/03/2017.
 */
public class ClientRepositoryMySQLTest {

    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setupClass() {
        clientRepository = new ClientRepositoryCacheDecorator(
                new ClientRepositoryMySQL(
                        new DBConnectionFactory().getConnectionWrapper(true).getConnection()
                ),
                new Cache<>()
        );
    }

    @Before
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {

        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        ClientBuilder clientBuilder=new ClientBuilder();
        //clientBuilder.setId(1L);
        clientBuilder.setName("First Client");
        clientBuilder.setPersonalNumericCode("1234567890123");
        clientBuilder.setAdress("Narnia");

        clientRepository.save(clientBuilder.build());
        //clientBuilder.setId(2L);
        clientBuilder.setPersonalNumericCode("2234567890123");
        clientRepository.save(clientBuilder.build());
        List<Client> clients = clientRepository.findAll();
        assertEquals(clients.size(), 2);
    }

    @Test
    public void findById() throws Exception {
        Client clientToBeFound;
        ClientBuilder clientBuilder=new ClientBuilder();
        //clientBuilder.setId(3L);
        clientBuilder.setName("Mr Martin");
        clientBuilder.setPersonalNumericCode("5234567890123");
        clientBuilder.setAdress("Narnia2");
        clientToBeFound=clientBuilder.build();

        if(clientRepository.save(clientToBeFound)){
            clientToBeFound.setId(clientRepository.findIDbyCNP(clientToBeFound.getPersonalNumericCode()));
            long idToBeFound=clientToBeFound.getId();
            Client foundClient=clientRepository.findById(idToBeFound).getResult();
            assertEquals(clientToBeFound.getId(),foundClient.getId());
        }else
        {
            System.out.println("Failed To Load a client into DB<<");
        }
    }

    @Test
    public void save() throws Exception {
        ClientBuilder clientBuilder=new ClientBuilder();
        //clientBuilder.setId(3L);
        clientBuilder.setName("Second Client");
        clientBuilder.setPersonalNumericCode("4234567890123");
        clientBuilder.setAdress("Narnia2");

        assertTrue(clientRepository.save(clientBuilder.build()));
    }

    @Test
    public void update() throws EntityNotFoundException {
        ClientBuilder clientBuilder=new ClientBuilder();
        //clientBuilder.setId(3L);
        clientBuilder.setName("Not Updated");
        clientBuilder.setPersonalNumericCode("7234567890123");
        clientBuilder.setAdress("Narnia");
        Client client=clientBuilder.build();
        clientRepository.save(client);
        client.setId(clientRepository.findIDbyCNP("7234567890123"));
        client.setName("Updated");
        clientRepository.updateClient(client);
        assertEquals("Updated",clientRepository.findById(client.getId()).getResult().getName());
    }

    @Test
    public void removeByID() throws EntityNotFoundException {
        ClientBuilder clientBuilder=new ClientBuilder();
        //clientBuilder.setId(3L);
        clientBuilder.setName("Not Updated");
        clientBuilder.setPersonalNumericCode("7234567890123");
        clientBuilder.setAdress("Narnia");
        Client client=clientBuilder.build();
        clientRepository.save(client);
        Long id=clientRepository.findIDbyCNP("7234567890123");
        clientRepository.removeByID(id);
        clientRepository.findById(id);
    }
    @Test
    public void removeAll() throws Exception {

    }

}