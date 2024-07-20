package com.work.client.controller;

import com.work.client.dto.MedicalDTO;
import com.work.client.service.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author linux
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/v1/api")
public class ClientController {

    private final Client client;

    public ClientController(Client client) {
        this.client = client;
    }

    @GetMapping(path = "/template/medical", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MedicalDTO> medicalRestTemplate(@RequestParam(defaultValue = "1") Integer userId, @RequestParam(defaultValue = "1") Integer page) {
        MedicalDTO response = this.client.callRestTemplate(userId, page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/feign/medical", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MedicalDTO> medicalFeign(@RequestParam(defaultValue = "1") Integer userId, @RequestParam(defaultValue = "1") Integer page) {
        MedicalDTO response = this.client.callFeign(userId, page);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
