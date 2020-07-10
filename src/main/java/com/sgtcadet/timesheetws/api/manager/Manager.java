package com.sgtcadet.timesheetws.api.manager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sgtcadet.timesheetws.api.client.Client;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NamedQuery(name="Manager.findAll", query="SELECT m FROM Manager m")
public class Manager implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String position;

    //bi-directional many-to-one association to Client
    @OneToMany(mappedBy="manager")
    @JsonIgnore
    private List<Client> clients;

    public Manager() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<Client> getClients() {
        return this.clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public Client addClient(Client client) {
        getClients().add(client);
        client.setManager(this);

        return client;
    }

    public Client removeClient(Client client) {
        getClients().remove(client);
        client.setManager(null);

        return client;
    }

}