package com.cognizant.services;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cognizant.dto.DefectDTO;
import com.cognizant.dto.DefectReportDTO;
import com.cognizant.dto.UpdateDefectDTO;
import com.cognizant.entities.Defect;

@Service
public interface DefectService {
	String createDefect(DefectDTO defectDTO) throws Exception;
	String updateDefect(UpdateDefectDTO defectDTO);
	List<Defect> findDefectsByDeveloper(String developerId) throws Exception;
	Optional<Defect> findDefectById(Integer id) throws Exception;
	DefectReportDTO generateDefectReport(int projectId) throws Exception;
	List<DefectDTO> getAllDefects();
}
