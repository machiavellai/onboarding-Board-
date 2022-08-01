package com.example.Ecoboard.Ecoboard.Service;

import com.example.Ecoboard.Ecoboard.dto.PersonResponse;
import com.example.Ecoboard.Ecoboard.model.Staff;
import com.example.Ecoboard.Ecoboard.model.VerificationToken;

import java.util.Optional;

public interface VerificationService {

    String saveVerificationToken(Staff person);

    Optional<VerificationToken> getToken(String token);

    void setConfirmedAt(String token);

    PersonResponse confirmToken(String token);
}


