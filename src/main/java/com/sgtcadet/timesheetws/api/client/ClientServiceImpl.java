package com.sgtcadet.timesheetws.api.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepository clientRepository;
    
    @Override
    public Client save(ClientDTO clientDto) {
        Client client = new Client();
        client.setName(clientDto.getClientName());
        return clientRepository.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}
