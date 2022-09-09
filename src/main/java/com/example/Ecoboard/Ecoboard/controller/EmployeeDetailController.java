package com.example.Ecoboard.Ecoboard.controller;

import com.example.Ecoboard.Ecoboard.Service.EmployeeDetailService;
import com.example.Ecoboard.Ecoboard.dto.EmployeeDetailsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/staff/EmployeeDetailsPage")
public class EmployeeDetailController {
    private final EmployeeDetailService employeeDetailService;


    @Autowired
    public EmployeeDetailController(EmployeeDetailService employeeDetailService) {
        this.employeeDetailService = employeeDetailService;
    }
    @PostMapping("/save")
    public ResponseEntity<EmployeeDetailsRequest> save(@Valid @RequestBody EmployeeDetailsRequest employeeDetailsRequest){
        return ResponseEntity.ok(employeeDetailService.save(employeeDetailsRequest));
    }



}
