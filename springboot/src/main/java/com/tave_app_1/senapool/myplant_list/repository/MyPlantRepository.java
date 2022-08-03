package com.tave_app_1.senapool.myplant_list.repository;

import com.tave_app_1.senapool.entity.MyPlant;
import com.tave_app_1.senapool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MyPlantRepository extends JpaRepository<MyPlant, Long> {

    //@Query(value = "SELECT * FROM myplant WHERE user_pk = :userPK", nativeQuery = true)
    //List<MyPlant> findPlantList(int userPK);

    MyPlant findByPlantPK(Long plantPK);

    MyPlant findAllByPlantPK(Long plantPK);

    Optional <MyPlant> deleteByUser(User user);
}
