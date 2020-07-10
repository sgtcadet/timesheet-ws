package com.sgtcadet.timesheetws.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping(path = "/clients")
    public ResponseEntity<Object> findAll(){
        return new ResponseEntity<>(clientService.findAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/clients")
    public ResponseEntity<Object> save(@RequestBody ClientDTO clientDTO){
        return new ResponseEntity<>(clientService.save(clientDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/")
    public String showAppMessage(){
        return "Hello World";
    }
}
