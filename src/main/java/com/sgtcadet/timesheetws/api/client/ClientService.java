package com.sgtcadet.timesheetws.api.client;

import java.util.List;

public interface ClientService {
    Client save(ClientDTO clientDTO);
    List<Client> findAll();
}
