package com.example.onlinehousingshowsample.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "housing")
public class Housing {


    @ManyToOne
    private Owner owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "housing_name",nullable = false)
    private String housingName;
    @Column(name = "address",nullable = false)
    private String address;
    @Column(name = "number_of_floors",nullable = false)
    private long numberOfFloors;
    @Column(name = "number_of_master_bedroom",nullable = false)
    private long numberOfMasterRoom;
    @Column(name = "number_of_single_room",nullable = false)
    private long numberOfSingleRoom;
    @Column(name = "amount",nullable = false)
    private long amount;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;
    @Column(name = "updated_date",nullable = false)
    private LocalDate updatedDate;


}
