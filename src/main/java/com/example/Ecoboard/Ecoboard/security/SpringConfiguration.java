package com.example.Ecoboard.Ecoboard.security;

import com.example.Ecoboard.Ecoboard.Service.serviceimplementation.StaffDetailsService;
import com.example.Ecoboard.Ecoboard.Utils.Api_Uri;
import com.example.Ecoboard.Ecoboard.security.filter.JwtRequestFilters;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringConfiguration extends WebSecurityConfigurerAdapter {

    private final StaffDetailsService staffDetailsService;
    private final JwtRequestFilters jwtRequestFilters;
    private final PasswordEncoder passwordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(staffDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
        .authorizeRequests().antMatchers(Api_Uri.PUBLIC_URIs)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                 .disable();

        http.addFilterBefore(jwtRequestFilters, UsernamePasswordAuthenticationFilter.class);
        http.cors();
    }


//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
//                .and().authorizeRequests()
//                .antMatchers(GET_AUTH_TOKEN,LOGIN,"/api/v1/user/register", "/api/v1/student/register", "/api/v1/student/login" ,"/api/v1/tokenPlain","/webjars/*", "/swagger-ui/*", "/v3/api-docs/*")
//                .permitAll()
//                .anyRequest().authenticated().and()
////				.addFilter(new JWTAuthorizationFilter(authenticationManager()))
//                // this disables session creation on Spring Security
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
