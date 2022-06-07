package com.example.springsecurity.service;

import com.example.springsecurity.entities.User;
import com.example.springsecurity.repositories.UserRepository;
import com.example.springsecurity.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired //notRecommend
    private UserRepository userRepository;

    @Override // usually more than 1
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 런타임 익셉션이므로 이걸 던져서 해결하려고 하지 말것
        // 익셉션 관련한 강좌 다시 확인할 것.
        Optional<User> o = userRepository.findUserByUsername(username);
        User u = o.orElseThrow(() -> new UsernameNotFoundException(":("));
        return new SecurityUser(u);
    }
}
