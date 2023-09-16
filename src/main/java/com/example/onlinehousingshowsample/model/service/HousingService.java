package com.example.onlinehousingshowsample.model.service;

import com.example.onlinehousingshowsample.model.dao.HousingDao;
import com.example.onlinehousingshowsample.model.dao.OwnerDao;
import com.example.onlinehousingshowsample.model.dto.HousingDto;
import com.example.onlinehousingshowsample.model.dto.form.HousingForm;
import com.example.onlinehousingshowsample.model.entity.Housing;
import com.example.onlinehousingshowsample.model.entity.Owner;
import com.example.onlinehousingshowsample.model.page.PagerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import io.jsonwebtoken.Jwts;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HousingService {
    @Autowired
    private OwnerDao ownerDao;
    @Autowired
    private HousingDao housingDao;

    public HousingDto save(HousingForm form, HttpHeaders headers, String loginToken){
//        Get username from token
        String token = headers.get("Authorization").get(0);
        String jwt = token.replace("Bearer","");
        String ownerUserName = Jwts.parser().setSigningKey(loginToken).parseClaimsJws(jwt).getBody().getSubject();

//Save house data
        Housing housing = new Housing();
        housing.setHousingName(form.getHousingName());
        housing.setAddress(form.getAddress());
        housing.setNumberOfFloors(form.getNumberOfFloors());
        housing.setNumberOfMasterRoom(form.getNumberOfMasterRoom());
        housing.setNumberOfSingleRoom(form.getNumberOfSingleRoom());
        housing.setAmount(form.getAmount());
        Owner owner = ownerDao.findByOwnerUserName(ownerUserName).orElseThrow(EntityNotFoundException::new);
        housing.setOwner(owner);
        housing.setCreatedDate(LocalDate.now());
        housing.setUpdatedDate(LocalDate.now());
        housingDao.save(housing);
        return HousingDto.housingData(housing);
    }

    @Transactional
    public HousingDto update(int id, HousingForm form,HttpHeaders headers,String loginToken){
        //        Getting username from token
        String token = headers.get("Authorization").get(0);
        String jwt = token.replace("Bearer","");
        String ownerUserName = Jwts.parser().setSigningKey(loginToken).parseClaimsJws(jwt).getBody().getSubject();

// Updating housing data
        return HousingDto.housingData(housingDao.findById(id).map(ele->{
            ele.setHousingName(form.getHousingName());
            ele.setAddress(form.getAddress());
            ele.setNumberOfFloors(form.getNumberOfFloors());
            ele.setNumberOfMasterRoom(form.getNumberOfMasterRoom());
            ele.setNumberOfSingleRoom(form.getNumberOfSingleRoom());
            ele.setAmount(form.getAmount());
            Owner owner = ownerDao.findByOwnerUserName(ownerUserName).orElseThrow(EntityNotFoundException::new);
            ele.setOwner(owner);
            ele.setUpdatedDate(LocalDate.now());
            return ele;
        }).orElseThrow(EntityNotFoundException::new));
    }

    //Finding Housing Data Using OwnerUserName from token
    public List<HousingDto> findByOwnerUserName(HttpHeaders headers, String loginToken,
                                                Optional<String> housingName, Optional<Integer> floors,
                                                Optional<Integer> masterRoom, Optional<Integer> singleRoom,
                                                Optional<Integer> amount, Optional<LocalDate> createdDate,
                                                int current, int size){
        String token = headers.get("Authorization").get(0);
        String jwt = token.replace("Bearer","");
        String ownerUserName = Jwts.parser().setSigningKey(loginToken).parseClaimsJws(jwt).getBody().getSubject();

        Specification<Housing> spec = withHousingName(housingName).and(withFloors(floors)).and(withMasterRoom(masterRoom))
                .and(withSingleRoom(singleRoom)).and(withAmount(amount)).and(withCreatedDate(createdDate));

        return housingDao.findAllByOwnerOwnerUserName(ownerUserName,housingName.orElse(null),floors.orElse(null)
                        ,masterRoom.orElse(null),singleRoom.orElse(null),amount.orElse(null),createdDate.orElse(null), PageRequest.of(current,size))
                .stream().map(ele -> HousingDto.housingData(ele)).collect(Collectors.toList());
    }

    //Find All Housing Data (Public Api)
    public PagerResult<HousingDto> findAllHousingData(Optional<String> housingName, Optional<Integer> floors,
                                                      Optional<Integer> masterRoom, Optional<Integer> singleRoom,
                                                      Optional<Integer> amount, Optional<LocalDate> createdDate,
                                                      int current, int size){
        var specification = withHousingName(housingName)
                .and(withFloors(floors))
                .and(withMasterRoom(masterRoom))
                .and(withSingleRoom(singleRoom))
                .and(withAmount(amount))
                .and(withCreatedDate(createdDate));

        var page = housingDao.findAll(specification,PageRequest.of(current,size))
                .map(HousingDto::housingData);

        return PagerResult.from(page);
    }

    private Specification<Housing> withHousingNameTest(Optional<String> housingName,String ownerUsername) {
        if(housingName.filter(StringUtils::hasLength).isPresent()) {
            Specification data = (root, query, cb) -> cb.like(cb.lower(root.get("housingName")), housingName.get().toLowerCase().concat("%"));
            if (data.equals(ownerUsername)){
                return data;
            }else{
                return null;
            }
        }

        return Specification.where(null);
    }

    private Specification<Housing> withHousingName(Optional<String> housingName) {
        if(housingName.filter(StringUtils::hasLength).isPresent()) {
            return (root, query, cb) -> cb.like(cb.lower(root.get("housingName")), housingName.get().toLowerCase().concat("%"));
        }

        return Specification.where(null);
    }

    private Specification<Housing> withFloors(Optional<Integer> floors){
        if (floors.filter(a-> a>0).isPresent()){
            return (root,query,cb) -> cb.equal(root.get("numberOfFloors"), floors.get());
        }
        return Specification.where(null);
    }

    private Specification<Housing> withMasterRoom(Optional<Integer> masterRoom){
        if (masterRoom.filter(a-> a>0).isPresent()){
            return (root,query,cb) -> cb.equal(root.get("numberOfMasterRoom"), masterRoom.get());
        }
        return Specification.where(null);
    }

    private Specification<Housing> withSingleRoom(Optional<Integer> singleRoom){
        if (singleRoom.filter(a-> a>0).isPresent()){
            return (root,query,cb) -> cb.equal(root.get("numberOfSingleRoom"), singleRoom.get());
        }
        return Specification.where(null);
    }

    private Specification<Housing> withAmount(Optional<Integer> amount){
        if (amount.filter(a-> a>0).isPresent()){
            return (root,query,cb) -> cb.equal(root.get("amount"), amount.get());
        }
        return Specification.where(null);
    }

    public Specification<Housing> withCreatedDate(Optional<LocalDate> createdDate) {
        if(createdDate.isPresent()) {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdDate"), createdDate.get());
        }

        return Specification.where(null);
    }
}
