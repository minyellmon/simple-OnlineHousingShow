package com.example.onlinehousingshowsample.model.dto;

import com.example.onlinehousingshowsample.model.entity.Owner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {

    private int id;
    private String ownerUserName;
    private String ownerName;
    private String ownerEmail;
    private String password;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private String jwtToken;
    public static OwnerDto ownerData(Owner owner, String jwtToken){
        return new OwnerDto(owner.getId(),
                owner.getOwnerUserName(),owner.getOwnerName(),owner.getOwnerEmail(),
                owner.getPassword(),owner.getCreatedDate(),owner.getUpdatedDate(),jwtToken);

    }
}
