package com.example.Ecoboard.Ecoboard.controller;

import com.example.Ecoboard.Ecoboard.Service.EmergencyContactsService;
import com.example.Ecoboard.Ecoboard.dto.EmergencyContactsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/staff/EmergencyContactsPage")
public class EmergencyContactsController {
    private final EmergencyContactsService emergencyContactsService;

    @Autowired
    public EmergencyContactsController(EmergencyContactsService emergencyContactsService) {
        this.emergencyContactsService = emergencyContactsService;
    }

    @PostMapping("/save")
    public ResponseEntity<EmergencyContactsRequest> save(@Valid @RequestBody EmergencyContactsRequest emergencyContactsRequest){
        return ResponseEntity.ok(emergencyContactsService.save(emergencyContactsRequest));
    }
}
