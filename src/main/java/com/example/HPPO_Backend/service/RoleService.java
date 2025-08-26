package com.example.HPPO_Backend.service;

import com.example.HPPO_Backend.entity.Role;
import com.example.HPPO_Backend.entity.dto.RoleRequest;
import com.example.HPPO_Backend.exceptions.RoleDuplicateException;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> getRoles();
    Optional<Role> getRoleById(Long roleId);
    Role createRole(RoleRequest roleRequest) throws RoleDuplicateException;
}
