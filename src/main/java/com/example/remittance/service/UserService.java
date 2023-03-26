package com.example.remittance.service;

import com.example.remittance.domain.User;
import com.example.remittance.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void save(User user){
        userRepository.save(user);
    }

    public User findByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow(Exception::new);
    }


}
