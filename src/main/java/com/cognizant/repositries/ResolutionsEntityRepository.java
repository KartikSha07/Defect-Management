package com.cognizant.repositries;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.entities.Defect;
import com.cognizant.entities.Resolution;


@Repository
public interface ResolutionsEntityRepository extends CrudRepository<Resolution, Integer> {
    List<Resolution> findByDefect(Defect defect);

}
