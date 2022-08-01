package com.example.Ecoboard.Ecoboard.Service.serviceimplementation;


import com.example.Ecoboard.Ecoboard.model.Staff;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Data
public class StaffDetails implements UserDetails {

    private String email;
    private String password;
    private boolean active;
    private String userName;


    public StaffDetails(Staff staff) {
        this.email = staff.getEmail();
        this.password = staff.getPassword();
        this.active = staff.isVerifyEmail();
        this.userName = staff.getUserName();
    }

    public StaffDetails() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}