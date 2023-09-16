package com.example.onlinehousingshowsample.controller;

import com.example.onlinehousingshowsample.model.dto.OwnerDto;
import com.example.onlinehousingshowsample.model.dto.form.OwnerForm;
import com.example.onlinehousingshowsample.model.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @PostMapping(path = "/register",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public OwnerDto register(@RequestParam("ownerUserName") String ownerUserName,
                             @RequestParam("ownerName") String ownerName,
                             @RequestParam("ownerEmail") String ownerEmail,
                             @RequestParam("password") String password) {
        OwnerForm ownerform = new OwnerForm();
        ownerform.setOwnerUserName(ownerUserName);
        ownerform.setOwnerName(ownerName);
        ownerform.setOwnerEmail(ownerEmail);
        ownerform.setOwnerPassword(password);
        return ownerService.save(ownerform);
    }

    @PostMapping(path = "/authenticate",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public OwnerDto authenticate(@RequestParam("ownerUserName")String ownerUserName,
                                 @RequestParam("password")String password){
        return ownerService.authenticate(ownerUserName,password);
    }
}
