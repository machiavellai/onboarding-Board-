package com.example.Ecoboard.Ecoboard.controller;

import com.example.Ecoboard.Ecoboard.Service.StaffService;
import com.example.Ecoboard.Ecoboard.Service.VerificationService;
import com.example.Ecoboard.Ecoboard.dto.*;
import com.example.Ecoboard.Ecoboard.model.Staff;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/staff")
@AllArgsConstructor
@CrossOrigin
public class StaffController {
    private final StaffService staffService;
    public final VerificationService verificationTokenService;

    @PutMapping("/profile/edit/personinfo")
    public ResponseEntity<UpdatePersonResponse> editUserDetails(@RequestBody UpdatePersonRequest updatePersonDetails) {
        return ResponseEntity.ok().body( staffService.updateUserDetails(updatePersonDetails));
    }

    @GetMapping("/profile")
    public ResponseEntity<PersonInfoResponse> getUserInfo() throws Exception {
        System.out.println("WE GOT HERE !!!");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(staffService.getInfo(authentication));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@Valid @RequestBody PersonRequest personRequest) throws IOException {
        return ResponseEntity.ok(staffService.register(personRequest));
    }
    @GetMapping("/confirm")
    public ResponseEntity<PersonResponse> confirm (@RequestParam("token") String token){
        return ResponseEntity.ok(verificationTokenService.confirmToken(token));
    }

    @PostMapping("/resend-token")
    public ResponseEntity<PersonResponse> resendingEmailToken (@RequestBody EmailTokenRequest tokenRequest) {
        return ResponseEntity.ok(staffService.sendingEmail(tokenRequest.getEmail()));
    }

    @PostMapping(path="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) throws Exception {
        return staffService.loginUser(req);
    }

    @PostMapping(path="/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> logout(@RequestBody AuthRequest req) throws Exception{
        return staffService.logoutUser(req);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> processResetPassword (@RequestBody EmailRequest resetEmail){
        return ResponseEntity.ok().body(staffService.resetPasswordToken(resetEmail.getEmail()));
    }

    @PutMapping("/update_password")
    public ResponseEntity<?> updatePassword(@RequestBody ResetPasswordRequest passwordRequest, @RequestParam(value = "email") String email){
        return ResponseEntity.ok().body(staffService.updateResetPassword(passwordRequest, email));

    }
//    @PostMapping(value = {"/addStaff_Image"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public Staff addNewStaffImage(@RequestBody Staff staff){
//        return
//    }
}
