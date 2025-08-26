package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Role;
import com.example.HPPO_Backend.entity.dto.RoleRequest;
import com.example.HPPO_Backend.exceptions.RoleDuplicateException;
import com.example.HPPO_Backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long roleId) {
        return roleRepository.findById(roleId);
    }

    public Role createRole(RoleRequest roleRequest) throws RoleDuplicateException {
        List<Role> roles = roleRepository.findByName(roleRequest.getName());
        if (roles.isEmpty()) {
            Role role = new Role();
            role.setName(roleRequest.getName());
            return roleRepository.save(role);
        }
        throw new RoleDuplicateException();
    }
}
