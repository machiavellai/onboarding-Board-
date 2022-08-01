package com.example.Ecoboard.Ecoboard.Service.serviceimplementation;


import com.example.Ecoboard.Ecoboard.Service.VerificationService;
import com.example.Ecoboard.Ecoboard.dto.PersonResponse;
import com.example.Ecoboard.Ecoboard.exception.CustomServiceException;
import com.example.Ecoboard.Ecoboard.model.Staff;
import com.example.Ecoboard.Ecoboard.model.VerificationToken;
import com.example.Ecoboard.Ecoboard.repository.StaffRepository;
import com.example.Ecoboard.Ecoboard.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationService {

    private final VerificationTokenRepository verificationTokenRepository;
    public final StaffRepository staffRepository;

    public String saveVerificationToken(Staff staff){
        final String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(
                token, LocalDateTime.now(), LocalDateTime.now().plusHours(24), staff);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public Optional<VerificationToken> getToken(String token){
        return verificationTokenRepository.findByTokenCode(token);
    }

    public void setConfirmedAt(String token){
        verificationTokenRepository.findByTokenCode(token).ifPresent((confirm) ->
                confirm.setConfirmedAt(LocalDateTime.now()));
    }

    @Transactional
    public PersonResponse confirmToken(String token){
        VerificationToken verificationToken = getToken(token)
                .orElseThrow(() -> new CustomServiceException("token not found"));

        if(verificationToken.getConfirmedAt() != null){
            throw new CustomServiceException("email already confirmed");
        }

        LocalDateTime expiresAt = verificationToken.getExpiresAt();

        if(expiresAt.isBefore(LocalDateTime.now())){
            throw new CustomServiceException("token expired");
        }

        setConfirmedAt(token);
        staffRepository.findByEmail(verificationToken.getStaff().getEmail()).ifPresent((staff) ->
                staff.setVerifyEmail(true));
        return PersonResponse.builder().message("Email verified").build();
    }

}
