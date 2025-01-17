package com.dimas.BassicBlog.Service;

import com.dimas.BassicBlog.Entity.Users;
import com.dimas.BassicBlog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<Users> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> saveUser(Users user) {
        return Optional.of(userRepository.save(user));
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
