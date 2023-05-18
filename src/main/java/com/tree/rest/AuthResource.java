package com.tree.rest;

import com.tree.entity.Statement;
import com.tree.service.StatementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(origins = {"*"})
public class AuthResource {

    Logger logger = LoggerFactory.getLogger(AuthResource.class);

    @PostMapping(value = "/login", produces = { "application/json" })
    public ResponseEntity<String> login(){
        logger.info("trying to login");
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String role = authorities.stream().findFirst().get().getAuthority();
        return ResponseEntity.ok(role);
    }


}
