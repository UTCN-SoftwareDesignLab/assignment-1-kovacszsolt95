package repository.client;

import model.Book;
import model.Client;
import model.builder.BookBuilder;
import model.builder.ClientBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;
import repository.EntityNotFoundException;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMock;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex on 08/03/2017.
 */
public class ClientRepositoryMockTest {

    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setupClass() {
        clientRepository = new ClientRepositoryCacheDecorator(
                new ClientRepositoryMock(),
                new Cache<>()
        );
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findAll() throws Exception {
        assertTrue(clientRepository.findAll().size() == 0);
    }

    @Test
    public void findById() throws Exception {
        ClientBuilder clientBuilder=new ClientBuilder();
        clientBuilder.setId(1L);
        clientBuilder.setName("First Client");
        clientBuilder.setPersonalNumericCode("1234567890123");
        clientBuilder.setAdress("Narnia");
        Client client=clientBuilder.build();
        clientRepository.save(client);
        Client savedClient=clientRepository.findById(1L).getResult();
        assertEquals(client.getId(),savedClient.getId());
    }

    @Test
    public void save() throws Exception {
        ClientBuilder clientBuilder=new ClientBuilder();
        clientBuilder.setId(1L);
        clientBuilder.setName("First Client");
        clientBuilder.setPersonalNumericCode("1234567890123");
        clientBuilder.setAdress("Narnia");

        assertTrue(clientRepository.save(clientBuilder.build()));
    }

}