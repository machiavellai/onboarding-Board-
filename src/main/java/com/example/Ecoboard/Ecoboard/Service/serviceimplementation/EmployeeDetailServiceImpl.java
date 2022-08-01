package com.example.Ecoboard.Ecoboard.Service.serviceimplementation;

import com.example.Ecoboard.Ecoboard.Service.EmployeeDetailService;
import com.example.Ecoboard.Ecoboard.dto.EmployeeDetailsRequest;
import com.example.Ecoboard.Ecoboard.model.EmployeeDetail;
import com.example.Ecoboard.Ecoboard.repository.EmployeeDetailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor

public class EmployeeDetailServiceImpl implements EmployeeDetailService {
    private final ModelMapper modelMapper;
    private final EmployeeDetailRepository employeeDetailRepository;

    @Override
    public EmployeeDetailsRequest save(EmployeeDetailsRequest employeeDetailsRequest) {
        EmployeeDetail contact = new EmployeeDetail();
        modelMapper.map(employeeDetailsRequest, contact);
        employeeDetailRepository.save(contact);
        return employeeDetailsRequest;
    }
}
