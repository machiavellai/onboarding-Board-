package com.example.Ecoboard.Ecoboard.Service;

import java.io.IOException;

import com.example.Ecoboard.Ecoboard.dto.*;
import com.example.Ecoboard.Ecoboard.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


public interface StaffService {

    ResponseEntity<AuthResponse> loginUser(AuthRequest req) throws Exception;

    ResponseEntity<AuthResponse> logoutUser(AuthRequest req) throws Exception;

    PersonInfoResponse getInfo(Authentication authentication) throws Exception;

    UpdatePersonResponse updateUserDetails(UpdatePersonRequest updatePersonRequest);

    PersonResponse register(PersonRequest personRequest) throws IOException;

    ChangePasswordResponse updateCurrentPassword(ChangePasswordRequest changePasswordRequest);

    void resetPasswordMailSender(String email, String token) ;

    Page<Staff> getAllUsers(int pageNumber);

    PersonResponse resetPasswordToken(String email) ;

    PersonResponse updateResetPassword(ResetPasswordRequest passwordRequest, String email);

    PersonResponse sendingEmail(String email) ;

    String buildEmail(String name, String link);


}
