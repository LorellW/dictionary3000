package com.github.lorellw.dictionary3000.services;

import com.github.lorellw.dictionary3000.entities.Role;
import com.github.lorellw.dictionary3000.entities.User;
import com.github.lorellw.dictionary3000.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
//        if (user == null){
//            throw new UsernameNotFoundException("User is not found");
//        }
        return user;
    }

    public boolean addUser(User user){
        if (userRepository.findByUsername(user.getUsername()) != null){
            return false;
        }
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}
