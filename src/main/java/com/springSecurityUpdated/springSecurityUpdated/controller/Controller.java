package com.springSecurityUpdated.springSecurityUpdated.controller;


import com.springSecurityUpdated.springSecurityUpdated.model.OurUser;
import com.springSecurityUpdated.springSecurityUpdated.repository.OurUserRepo;
import com.springSecurityUpdated.springSecurityUpdated.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping
@RestController
public class Controller {

    @Autowired
    public OurUserRepo ourUserRepo;

    @Autowired
    public ProductRepo productRepo;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String goHome(){
        return "This is publicly accessible without needing authentication";
    }

    @PostMapping("/user/save")
    public ResponseEntity<Object> saveUser(@RequestBody OurUser ourUser){
        ourUser.setPassword(passwordEncoder.encode(ourUser.getPassword()));
        OurUser result = ourUserRepo.save(ourUser);
        if(result.getId() > 0){
            return ResponseEntity.ok("User was saved");
        }
        return ResponseEntity.status(404).body("Error: User not saved");
    }

    @PostMapping("/product/all")
    public ResponseEntity<Object> getAllProduct(){
        return ResponseEntity.ok(productRepo.findAll());
    }

    @PostMapping("/user/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllUser(){
        return ResponseEntity.ok(ourUserRepo.findAll());
    }
    @PostMapping("/user/single")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Object> getMyDetails(){
        return ResponseEntity.ok(ourUserRepo.findbyEmail(getLoggedInUserDetails().getUsername()));
    }
    public UserDetails getLoggedInUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails)
            return (UserDetails) authentication.getPrincipal();
        return null;
    }

}
