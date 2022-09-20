package com.example.Ecoboard.Ecoboard.Service.serviceimplementation;

import com.example.Ecoboard.Ecoboard.Email.EmailService;
import com.example.Ecoboard.Ecoboard.Service.StaffService;
import com.example.Ecoboard.Ecoboard.dto.*;
import com.example.Ecoboard.Ecoboard.exception.CustomServiceException;
import com.example.Ecoboard.Ecoboard.exception.PersonNotFoundException;
import com.example.Ecoboard.Ecoboard.model.Staff;
import com.example.Ecoboard.Ecoboard.repository.StaffRepository;
import com.example.Ecoboard.Ecoboard.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StaffServiceImpl implements StaffService {

    private final VerificationTokenServiceImpl verificationTokenService;

    private final PasswordEncoder bcryptPasswordEncoder;

   private final StaffDetailsService userDetailsService;

   private final AuthenticationManager authenticationManager;

   private final ModelMapper modelMapper;

    private final StaffRepository staffRepository;

    private final EmailValidator emailValidator;

    private final JwtUtils jwtUtils;

    private final EmailService emailSender;



    private String website;
    @Value("${server.port}")
    private Integer port;

    @Autowired
    public StaffServiceImpl(VerificationTokenServiceImpl verificationTokenService, PasswordEncoder bcryptPasswordEncoder, StaffDetailsService userDetailsService,
                            AuthenticationManager authenticationManager,
                            ModelMapper modelMapper,
                            StaffRepository staffRepository,
                            EmailValidator emailValidator,
                            JwtUtils jwtUtils,
                            EmailService emailSender) {
        this.verificationTokenService = verificationTokenService;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.staffRepository = staffRepository;
        this.emailValidator = emailValidator;
        this.jwtUtils = jwtUtils;
        this.emailSender = emailSender;
    }


    @Override
    public ResponseEntity<AuthResponse> loginUser(AuthRequest req) throws Exception {
        try {
             authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUserName(),
                    req.getPassword()));
            final StaffDetails staff = userDetailsService.loadUserByUsername(req.getUserName());
            List<String> roles = staff.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            final String jwt = jwtUtils.generateToken(staff);
            final AuthResponse res = new AuthResponse();

            String role =null;
            for (String r : roles) {
                if (r!=null) role = r;
            }
            final PersonInfoResponse userInfo = getUserInfo(req.getUserName());
            res.setToken(jwt);
            res.setRole(role);
            res.setUserInfo(userInfo);

            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            throw new Exception("Incorrect username or password!", e);
        }
    }

    @Override
    public ResponseEntity<AuthResponse> logoutUser(AuthRequest req) throws Exception {
        try {
            final StaffDetails staff = userDetailsService.loadUserByUsername(req.getUserName());
            List<String> roles = staff.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
            final AuthResponse res = new AuthResponse();
            String role =null;
            for (String r : roles) {
                if (r!=null) role = r;
            }
            return ResponseEntity.ok().body(res);

        } catch (Exception e) {
            throw new Exception("Incorrect username or password!", e);
        }
    }

    @Override
    public PersonInfoResponse getInfo(Authentication authentication) throws Exception {
        Staff staff = staffRepository.findByUserName(authentication.getName())
                .orElseThrow(()-> new PersonNotFoundException("Person Not Found"));
        PersonInfoResponse personInfoResponse = new PersonInfoResponse();
        modelMapper.map(staff, personInfoResponse);
//        personInfoResponse.setEmail(personInfoResponse.setUserName(personInfoResponse.getEmail()));
        return getUserInfo(authentication.getName());
    }

    private PersonInfoResponse getUserInfo(String username) {
        Staff staff = staffRepository.findByUserName(username)
                .orElseThrow(()-> new PersonNotFoundException("Person Not Found"));
        PersonInfoResponse personInfoResponse = new PersonInfoResponse();
        modelMapper.map(staff, personInfoResponse);
//        personInfoResponse.setDobText(personInfoResponse.setDate(personInfoResponse.getDateOfBirth()));
        return personInfoResponse;
    }

    @Override
    public UpdatePersonResponse updateUserDetails(UpdatePersonRequest updatePersonRequest) {
        Staff existingStaff = staffRepository.findPersonByUserName(updatePersonRequest.getUserName())
                .orElseThrow(
                        () -> new PersonNotFoundException("Person Not Found")
                );
        modelMapper.map(updatePersonRequest, existingStaff);
        staffRepository.save(existingStaff);
        UpdatePersonResponse response = new UpdatePersonResponse();
        modelMapper.map(existingStaff,response);
        return response;
    }
    @Override
    public PersonResponse register(PersonRequest personRequest) throws IOException {
        boolean isValidEmail = emailValidator.test(personRequest.getEmail());
        if(!isValidEmail){
            return PersonResponse.builder().message("Not a valid email").build();
        }

//        boolean isValidNumber = emailValidator.validatePhoneNumber(personRequest.getPhoneNumber());
//
//        if(!isValidNumber){
//            return PersonResponse.builder().message("Not a valid phone Number").build();
//        }

        boolean userExists = staffRepository.findByEmail(personRequest.getEmail()).isPresent();
        if(userExists){
            return PersonResponse.builder().message("email taken").build();
        }

        Staff staff = new Staff();
        modelMapper.map(personRequest, staff);

        final String encodedPassword = bcryptPasswordEncoder.encode(personRequest.getPassword());
        staff.setPassword(encodedPassword);
        String token = RandomString.make(64);
        staff.setResetPasswordToken(token);

        staffRepository.save(staff);
        sendingEmail(personRequest.getEmail());
        return PersonResponse.builder()
                .email(staff.getEmail()).message("Successful") .build();    }



    @Override
    public ChangePasswordResponse updateCurrentPassword(ChangePasswordRequest changePasswordRequest) {
        Staff currentStaff = staffRepository.findByUserName(changePasswordRequest.getUserName())
                .orElseThrow(()-> new PersonNotFoundException("Person Not Found"));
        String newPassword = changePasswordRequest.getNewPassword();
        String confirmPassword = changePasswordRequest.getConfirmPassword();


        if(bcryptPasswordEncoder.matches(changePasswordRequest.getCurrentPassword(), currentStaff.getPassword())){
            if (newPassword.equals(confirmPassword)) {
                currentStaff.setPassword(bcryptPasswordEncoder.encode(newPassword));
                staffRepository.save(currentStaff);
                return new ChangePasswordResponse("Password successfully changed");
            }
            else { return new ChangePasswordResponse("Confirm password does not match proposed password");
            }
        }
        else {
            return new ChangePasswordResponse("Incorrect current password");
        }
    }

    @Override
    public void resetPasswordMailSender(String email, String token) {
        String resetPasswordLink = "http://"+ website + ":" + port + "/update_password?token=" + token;
        String subject = "Here's the link to reset your password";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + resetPasswordLink + "\">Change my password</a></p>"
                + "<br>"
                + "<p> Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";
        emailSender.sendMessage(subject, email, content);
    }

    @Override
    public Page<Staff> getAllUsers(int pageNumber) {
        final List<Staff> personList = staffRepository.findAll();
        int pageSize = 10;
        int skipCount = (pageNumber - 1) * pageSize;
        List<Staff> usersPaginated = personList
                .stream()
                .skip(skipCount)
                .limit(pageSize)
                .collect(Collectors.toList());
        Pageable usersPage = PageRequest.of(pageNumber, pageSize, Sort.by("firstName").ascending());
        return new PageImpl<>(usersPaginated, usersPage, personList.size());
    }

    @Override
    public PersonResponse resetPasswordToken(String email) {
        Staff person = staffRepository.findByEmail(email)
                .orElseThrow(()-> new PersonNotFoundException("Email not Registered"));
        String token = RandomString.make(64);
        person.setResetPasswordToken(token);
        staffRepository.save(person);
        resetPasswordMailSender(person.getEmail(), token);
        return PersonResponse.builder().message("email sent").build();
    }

    @Override
    public PersonResponse updateResetPassword(ResetPasswordRequest passwordRequest, String email) {

        Staff staff = staffRepository.findByEmail(email)
                .orElseThrow(()-> new PersonNotFoundException("Person not found"));
        if(passwordRequest.getNewPassword().equals(passwordRequest.getConfirmPassword())){
            staff.setPassword(bcryptPasswordEncoder.encode(passwordRequest.getNewPassword()));
            staffRepository.save(staff);
            return PersonResponse.builder().message("updated").build();
        }
        return PersonResponse.builder().message("mismatch of new and confirm password").build();
    }

    @Override
    public PersonResponse sendingEmail(String email) {
        Staff staff = staffRepository.findByEmail(email)
                .orElseThrow(() -> new CustomServiceException("Email not registered"));
        String token = verificationTokenService.saveVerificationToken(staff);
        String link = "http://"+ website +"/person/confirm?token=" + token;
        String subject = "Confirm your email";
        emailSender.sendMessage(subject, staff.getEmail(), buildEmail(staff.getEmail(), link));
        return PersonResponse.builder().message("Email sent").build();
    }


    @Override
    public String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 24 hours. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}














