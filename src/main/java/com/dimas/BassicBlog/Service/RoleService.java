package com.dimas.BassicBlog.Service;

import com.dimas.BassicBlog.Entity.Role;
import com.dimas.BassicBlog.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
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

    public Optional<Role> deleteRole(Long id) {
        Optional<Role> deleteRole = roleRepository.findById(id);
        roleRepository.deleteById(id);
        return deleteRole;
    }

    public Optional<Role> findRoleById(Long id) {
        return roleRepository.findById(id);
    }

}
