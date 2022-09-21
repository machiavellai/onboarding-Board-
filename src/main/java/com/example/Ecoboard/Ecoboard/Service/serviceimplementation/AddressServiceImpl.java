package com.example.Ecoboard.Ecoboard.Service.serviceimplementation;

import com.example.Ecoboard.Ecoboard.Service.AddressService;
import com.example.Ecoboard.Ecoboard.dto.AddressRequest;
import com.example.Ecoboard.Ecoboard.model.Adress;
import com.example.Ecoboard.Ecoboard.repository.AddressRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;

    @Override
    public AddressRequest save(AddressRequest addressRequest) {
        Adress address = new Adress();
        modelMapper.map(addressRequest, address);
        addressRepository.save(address);
        return addressRequest;
    }
}
