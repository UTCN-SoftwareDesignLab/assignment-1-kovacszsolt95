package repository.client;

/**
 * Created by Alex on 07/03/2017.
 */
public abstract class ClientRepositoryDecorator implements ClientRepository {

    protected ClientRepository decoratedRepository;

    public ClientRepositoryDecorator(ClientRepository clientRepository) {
        this.decoratedRepository = clientRepository;
    }

}
