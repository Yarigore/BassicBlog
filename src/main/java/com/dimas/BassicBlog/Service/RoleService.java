package com.dimas.BassicBlog.Service;

import com.dimas.BassicBlog.Entity.Role;
import com.dimas.BassicBlog.Repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;


    public Optional<List<Role>> getRoles() {
        return Optional.of(roleRepository.findAll());
    }

    public Optional<Role> getRolebyId(Long id) {
        return roleRepository.findById(id);
    }

    public Optional<Role> saveRole(Role role) {
        return Optional.of(roleRepository.save(role));
    }

    public boolean deleteRole(Long id) {
        if (roleRepository.existsById(id)){
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
