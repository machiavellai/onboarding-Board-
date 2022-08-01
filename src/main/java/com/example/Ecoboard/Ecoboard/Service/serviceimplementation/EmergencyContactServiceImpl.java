package com.example.Ecoboard.Ecoboard.Service.serviceimplementation;

import com.example.Ecoboard.Ecoboard.Service.EmergencyContactsService;
import com.example.Ecoboard.Ecoboard.dto.EmergencyContactsRequest;
import com.example.Ecoboard.Ecoboard.model.EmergencyContact;
import com.example.Ecoboard.Ecoboard.repository.EmergencyContactRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EmergencyContactServiceImpl implements EmergencyContactsService {
    private final ModelMapper modelMapper;
    private final EmergencyContactRepository emergencyContactRepository;


    @Override
    public EmergencyContactsRequest save(EmergencyContactsRequest emergencyContactsRequest) {
        EmergencyContact emergencyContact = new EmergencyContact();
        modelMapper.map(emergencyContactsRequest, emergencyContact);
        emergencyContactRepository.save(emergencyContact);
        return emergencyContactsRequest;
    }
}
