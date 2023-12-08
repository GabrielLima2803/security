package com.alibou.security.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.alibou.security.user.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(User user) {
        Collection<? extends GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());

        return new UserPrincipal(user, authorities);
    }

    // Adicione este construtor
    public UserPrincipal(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.user = new User();
        this.user.setUsername(username);
        this.user.setPassword(password);
        this.authorities = authorities;
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    public User getUser() {
        return user;
    }
    
}
