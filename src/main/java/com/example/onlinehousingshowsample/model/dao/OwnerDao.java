package com.example.onlinehousingshowsample.model.dao;

import com.example.onlinehousingshowsample.model.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OwnerDao extends JpaRepository<Owner,Integer> {
    Optional<Owner> findByOwnerUserName(String ownerUserName);
}
