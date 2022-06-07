package com.example.springsecurity.security.filter;

import com.example.springsecurity.entities.Otp;
import com.example.springsecurity.repositories.OtpRepository;
import com.example.springsecurity.security.authentications.OtpAuthentication;
import com.example.springsecurity.security.authentications.UsernamePasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    //리퀘스트가 authenticationManager로 가므로
    //
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpRepository otpRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Step 1: username & password
        // Step 2: username & otp

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String otp = request.getHeader("otp");

        if(otp == null){
            // step 1
            Authentication a = new UsernamePasswordAuthentication(username, password);
            authenticationManager.authenticate(a);
            // we generate an OTP
            String code = String.valueOf(new Random().nextInt(9999)+1000);
            Otp otpEntity = new Otp();
            otpEntity.setUsername(username);
            otpEntity.setOtp(code);
            otpRepository.save(otpEntity);
        } else {
            // step 2
            Authentication a = new OtpAuthentication(username, otp);
            authenticationManager.authenticate(a);
            // we issue a token
            response.setHeader("Authorization", UUID.randomUUID().toString());
        }

    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        return !request.getServletPath().equals("/login");
    }
}
