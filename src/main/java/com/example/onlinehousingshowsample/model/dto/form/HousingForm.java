package com.example.onlinehousingshowsample.model.dto.form;

import lombok.*;

@Data
public class HousingForm {

    private String housingName;
    private String address;
    private long numberOfFloors;
    private long numberOfMasterRoom;
    private long numberOfSingleRoom;
    private long amount;
    private long owner;

    public HousingForm() {
    }

    public HousingForm(String housingName, String address, long numberOfFloors, long numberOfMasterRoom, long numberOfSingleRoom, long amount, long owner) {
        this.housingName = housingName;
        this.address = address;
        this.numberOfFloors = numberOfFloors;
        this.numberOfMasterRoom = numberOfMasterRoom;
        this.numberOfSingleRoom = numberOfSingleRoom;
        this.amount = amount;
        this.owner = owner;
    }
}
