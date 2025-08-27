package com.example.HPPO_Backend.controllers;

import com.example.HPPO_Backend.entity.Role;
import com.example.HPPO_Backend.entity.dto.RoleRequest;
import com.example.HPPO_Backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("roles")
public class RolesController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(this.roleService.getRoles());
    }

    @GetMapping({"/{roleId}"})
    public ResponseEntity<Role> getRoleById(@PathVariable Long roleId) {
        Optional<Role> result = this.roleService.getRoleById(roleId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Object> createRole(@RequestBody RoleRequest roleRequest) throws Exception {
        Role result = this.roleService.createRole(roleRequest);
        return ResponseEntity.created(URI.create("/roles/" + result.getId())).body(result);
    }
}
