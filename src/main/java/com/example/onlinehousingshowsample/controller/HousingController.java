package com.example.onlinehousingshowsample.controller;

import com.example.onlinehousingshowsample.model.dto.HousingDto;
import com.example.onlinehousingshowsample.model.dto.form.HousingForm;
import com.example.onlinehousingshowsample.model.page.PagerResult;
import com.example.onlinehousingshowsample.model.service.HousingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/housing")
public class HousingController {

    @Autowired
    private HousingService housingService;

    @Value("${myapp.secretKey}")
    private String SECRET_KEY;

    //create house with login owner
    @PostMapping("/create")
    public HousingDto create(@RequestBody @Validated HousingForm form, BindingResult result, @RequestHeader HttpHeaders headers){
        if (result.hasErrors()){
            throw  new EntityNotFoundException();
        }
        return housingService.save(form,headers,SECRET_KEY);
    }

    //update housing data with login owner
    @PatchMapping("{id}")
    public HousingDto update(@PathVariable int id, @RequestBody @Validated HousingForm form, BindingResult result, @RequestHeader HttpHeaders headers){
        if (result.hasErrors()){
            throw new EntityNotFoundException();
        }
        return housingService.update(id,form,headers,SECRET_KEY);
    }


    //Find Housing Data With LoggedIn Owner
    @GetMapping("/owner-search")
    public List<HousingDto> findByOwnerUserName(@RequestHeader HttpHeaders headers,
                                                @RequestParam Optional<String> housingName,
                                                @RequestParam("numberOfFloors") Optional<Integer> floors,
                                                @RequestParam Optional<Integer> masterRoom,
                                                @RequestParam Optional<Integer> singleRoom,
                                                @RequestParam Optional<Integer> amount,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> createdDate,
                                                @RequestParam(required = false,defaultValue = "0")int current,
                                                @RequestParam(required = false,defaultValue = "2")int size){
        return housingService.findByOwnerUserName(headers,SECRET_KEY,housingName,floors,masterRoom,singleRoom,amount,createdDate,current,size);
    }

    //    Finding All Housing Data (Public Api)
    @GetMapping("/public-search")
    public PagerResult<HousingDto> findAll(@RequestParam Optional<String> housingName,
                                           @RequestParam("numberOfFloors") Optional<Integer> floors,
                                           @RequestParam Optional<Integer> masterRoom,
                                           @RequestParam Optional<Integer> singleRoom,
                                           @RequestParam Optional<Integer> amount,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> createdDate,
                                           @RequestParam(required = false,defaultValue = "0")int current,
                                           @RequestParam(required = false,defaultValue = "2")int size){
        return housingService.findAllHousingData(housingName,floors,masterRoom,singleRoom,amount,createdDate,current,size);
    }

}
