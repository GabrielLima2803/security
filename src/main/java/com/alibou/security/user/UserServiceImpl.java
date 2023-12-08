package com.alibou.security.user;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alibou.security.security.UserPrincipal;

@Service
public class UserServiceImpl implements UserService {

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserRepository userRepository;

    @Override
    public User create(User user) {
        User existUser = userRepository.findByUsername(user.getUsername());
        if (existUser != null) {
            throw new Error("Usuário já existe");
        }

        user.setPassword(passwordEncoder().encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        return createdUser;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User authenticate(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username, null);

        if (!passwordEncoder().matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return ((UserPrincipal) userDetails).getUser();
    }

    private UserDetails loadUserByUsername(String username, Collection<? extends GrantedAuthority> password) {
        User user = findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new UserPrincipal(user, password);
    }
}
