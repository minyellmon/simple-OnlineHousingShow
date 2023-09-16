package com.example.onlinehousingshowsample.model.service;

import com.example.onlinehousingshowsample.model.dao.OwnerDao;
import com.example.onlinehousingshowsample.model.dto.OwnerDto;
import com.example.onlinehousingshowsample.model.entity.Owner;
import com.example.onlinehousingshowsample.model.entity.Role;
import com.example.onlinehousingshowsample.model.dto.form.OwnerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OwnerService {

    @Autowired
    private OwnerDao ownerDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;


    //create owner
    public OwnerDto save(OwnerForm form){
        Owner owner = new Owner();
        owner.setOwnerUserName(form.getOwnerUserName());
        owner.setOwnerName(form.getOwnerName());
        owner.setOwnerEmail(form.getOwnerEmail());
        owner.setPassword(passwordEncoder.encode(form.getOwnerPassword()));
        owner.setCreatedDate(LocalDate.now());
        owner.setUpdatedDate(LocalDate.now());
        owner.setRole(Role.OWNER);
        ownerDao.save(owner);
        var jwtToken = jwtService.generateToken((UserDetails) owner);
        return OwnerDto.ownerData(owner,jwtToken);
    }

    //owner Login
    public OwnerDto authenticate(String ownerUserName,String password){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        ownerUserName,password
                )
        );
        var user = ownerDao.findByOwnerUserName(ownerUserName).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return OwnerDto.ownerData(user,jwtToken);

    }

}
