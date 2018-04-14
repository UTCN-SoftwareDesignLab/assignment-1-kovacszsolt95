package repository.account;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;
import repository.EntityNotFoundException;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryCacheDecorator;
import repository.client.ClientRepositoryMySQL;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountRepositoryMySQLTest {
        private static AccountRepository accountRepository;
        private static ClientRepository clientRepository;

        @BeforeClass
        public static void setupClass() {
            accountRepository = new AccountRepositoryCacheDecorator(
                    new AccountRepositoryMySQL(
                            new DBConnectionFactory().getConnectionWrapper(true).getConnection()
                    ),
                    new Cache<>()
            );
            clientRepository = new ClientRepositoryCacheDecorator(
                    new ClientRepositoryMySQL(
                            new DBConnectionFactory().getConnectionWrapper(true).getConnection()
                    ),
                    new Cache<>()
            );
        }

        @Before
        public void cleanUp() {
            accountRepository.removeAll();
        }

        @Test
        public void findAll() throws Exception {
            List<Account> accounts = accountRepository.findAll();
            assertEquals(accounts.size(), 0);
        }

        @Test
        public void findAllWhenDbNotEmpty() throws Exception {
            long ownerID;
            List<Client> clients= clientRepository.findAll();
            if(clients.size()>0){
                ownerID=clients.get(0).getId();
            }else{
                throw new EntityNotFoundException(null,"The Client table is empty, no Account owner could be found");
            }
            accountRepository.removeAll();
            AccountBuilder accountBuilder=new AccountBuilder();
            accountBuilder.setId(1L);
            accountBuilder.setOwnerId(ownerID);
            accountBuilder.setType("Saving");
            accountBuilder.setBalance(0L);
            accountBuilder.setDateOfCreation(new Date());
            accountRepository.save(accountBuilder.build());


            List<Account> accounts = accountRepository.findAll();
            assertEquals(accounts.size(), 1);
        }

        @Test
        public void findById() throws Exception {

        }

        @Test
        public void save() throws Exception {
            long ownerID;
            List<Client> clients= clientRepository.findAll();
            if(clients.size()>0){
                ownerID=clients.get(0).getId();
            }else{
                throw new EntityNotFoundException(null,"The Client table is empty");
            }
            AccountBuilder accountBuilder=new AccountBuilder();
            accountBuilder.setOwnerId(ownerID);
            accountBuilder.setId(1L);
            accountBuilder.setType("Saving");
            accountBuilder.setBalance(0L);
            accountBuilder.setDateOfCreation(new Date());
            assertTrue(accountRepository.save(accountBuilder.build()));
        }

        @Test
        public void removeAll() throws Exception {

        }



}
