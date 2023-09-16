package com.example.onlinehousingshowsample.model.dto;

import com.example.onlinehousingshowsample.model.entity.Housing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HousingDto {
    private int id;
    private String housingName;
    private String address;
    private long numberOfFloors;
    private long numberOfMasterRoom;
    private long numberOfSingleRoom;
    private long amount;
    private long ownerId;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    public static HousingDto housingData(Housing housing){
        return new HousingDto(housing.getId(),
                housing.getHousingName(),
                housing.getAddress(),
                housing.getNumberOfFloors(),
                housing.getNumberOfMasterRoom(),
                housing.getNumberOfSingleRoom(),
                housing.getAmount(),
                housing.getOwner().getId(),
                housing.getCreatedDate(),
                housing.getUpdatedDate());
    }


}
