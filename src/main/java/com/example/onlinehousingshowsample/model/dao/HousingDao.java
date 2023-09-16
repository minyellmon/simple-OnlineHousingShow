package com.example.onlinehousingshowsample.model.dao;

import com.example.onlinehousingshowsample.model.entity.Housing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface HousingDao extends JpaRepository<Housing,Integer>, JpaSpecificationExecutor<Housing> {

    @Query("SELECT h FROM Housing h WHERE h.owner.ownerUserName = :ownerUserName " +
            "AND (:housingName IS NULL OR h.housingName LIKE %:housingName%) " +
            "AND (:floors IS NULL OR h.numberOfFloors = :floors OR (:floors = 0 AND h.numberOfFloors IS NULL)) " +
            "AND (:masterRoom IS NULL OR h.numberOfMasterRoom = :masterRoom) " +
            "AND (:singleRoom IS NULL OR h.numberOfSingleRoom = :singleRoom) " +
            "AND (:amount IS NULL OR h.amount = :amount) " +
            "AND (:createdDate IS NULL OR h.createdDate = :createdDate)")

    List<Housing> findAllByOwnerOwnerUserName(String ownerUserName, String housingName,
                                              Integer floors, Integer masterRoom,
                                              Integer singleRoom, Integer amount,
                                              LocalDate createdDate, Pageable pageable);


}
