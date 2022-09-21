package com.example.Ecoboard.Ecoboard.controller;


import com.example.Ecoboard.Ecoboard.Service.AddressService;
import com.example.Ecoboard.Ecoboard.dto.AddressRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/staff/AddressForm")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @PostMapping("/save")
    public ResponseEntity<AddressRequest>save (@Valid @RequestBody AddressRequest addressRequest){
        return ResponseEntity.ok(addressService.save(addressRequest));
    }
}
