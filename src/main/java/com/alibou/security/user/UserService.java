package com.alibou.security.user;

public interface UserService {
    User create(User user);
    User findByUsername(String username);
    User authenticate(String username, String password);  
}
