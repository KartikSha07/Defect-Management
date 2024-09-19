package com.cognizant.repositries;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.entities.Defect;

@Repository
public interface DefectEntityRepository extends CrudRepository<Defect, Integer> {
	
    List<Defect> findByAssignedtodeveloperid(String developerId);
    List<Defect> findByProjectcode(int projectcode);

}
