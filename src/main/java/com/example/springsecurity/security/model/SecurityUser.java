package com.example.springsecurity.security.model;

import com.example.springsecurity.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {//userDetail로 가져와야 스프링시큐리티가 유저로 인식할 수 있음.

    private final User user; //final을 지정해서 생성자를 유도함으로써 엔티티에서 wrapper로 감쌀 때  절대로 null이 되지 않도록 함.

    public SecurityUser(User user){
        this.user =  user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()->"read");
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
