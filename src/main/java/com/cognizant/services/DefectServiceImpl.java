package com.cognizant.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.dto.DefectDTO;
import com.cognizant.dto.DefectReportDTO;
import com.cognizant.dto.ResolutionDTO;
import com.cognizant.dto.UpdateDefectDTO;
import com.cognizant.dto.UpdateResolutionDTO;
import com.cognizant.entities.Defect;
import com.cognizant.entities.Resolution;
import com.cognizant.repositries.DefectEntityRepository;

@Service("defectServiceImpl")
public class DefectServiceImpl implements DefectService {
	
	@Autowired
	private DefectEntityRepository defectRepository;
	
		
	private final Map<String, Map<LocalDate, Integer>> bugsAssignedPerDeveloperPerDay = new HashMap<>();

	  @Autowired
	  private BugResolutionCalculator resolutionCalculator;
	
	@Override
	public String createDefect(DefectDTO defectDTO) throws Exception {
		try {
	        if (defectDTO.getStepstoreproduce() == null) {
	            throw new IllegalArgumentException("Stepstoreproduce is required");
	        }
	        String developerId = defectDTO.getAssignedtodeveloperid();
	        LocalDate currentDate = LocalDate.now();
	        int maxBugsPerDay = 5;
	        Map<LocalDate, Integer> developerBugs = bugsAssignedPerDeveloperPerDay.computeIfAbsent(developerId, k -> new HashMap<>());
	        int bugsAssignedToday = developerBugs.getOrDefault(currentDate, 0);
	        if (bugsAssignedToday >= maxBugsPerDay) {
	            return "Developer has reached the maximum bug assignment limit for today";
	        }
	        Defect defect = convertToEntity(defectDTO);
	        defect.setAssignedtodeveloperid(developerId); // Ensure developer is set
	        developerBugs.put(currentDate, bugsAssignedToday + 1);
	        Defect savedDefect = defectRepository.save(defect);
	        if (savedDefect != null) {
	            return "success";
	        } else {
	            return "fail";
	        }
	    } catch(Exception e) {
	        throw new Exception(e.getMessage());
	    }
	}
 
    private Defect convertToEntity(DefectDTO defectDTO) 
    {
        LocalDate expectedResolutionDate = resolutionCalculator.calculateExpectedResolutionDate(defectDTO.getSeverity(), defectDTO.getPriority(), defectDTO.getDetectedon());

        Defect defect = new Defect();
        defect.setTitle(defectDTO.getTitle());
        defect.setDefectdetails(defectDTO.getDefectdetails());
        defect.setStepstoreproduce(defectDTO.getStepstoreproduce());
        defect.setPriority(defectDTO.getPriority());
        defect.setDetectedon(defectDTO.getDetectedon());
        defect.setExpectedresolution(expectedResolutionDate);        
        defect.setReportedbytesterid(defectDTO.getReportedbytesterid());
        defect.setSeverity(defectDTO.getSeverity());
        defect.setStatus(defectDTO.getStatus());
        defect.setProjectcode(defectDTO.getProjectcode());
 
        if (defectDTO.getResolutions() != null && !defectDTO.getResolutions().isEmpty()) {
            for (ResolutionDTO resolutionDTO : defectDTO.getResolutions()) {
                Resolution resolution = new Resolution();
                resolution.setDefect(defect);
                resolution.setResolutiondate(resolutionDTO.getResolutiondate());
                resolution.setResolution(resolutionDTO.getResolution());
                defect.getResolutions().add(resolution);
            }
        }
 
        return defect;
    }
    

    
    @Override
    public String updateDefect(UpdateDefectDTO defectDTO) 
    {
    	
        Integer id = defectDTO.getId();
        Optional<Defect> optionalDefect = defectRepository.findById(id);
        if (optionalDefect.isPresent()) {
            Defect defect = optionalDefect.get();
            
            defect.setStatus(defectDTO.getStatus());
            if (defectDTO.getResolutions() != null && !defectDTO.getResolutions().isEmpty()) {
            	List<Resolution> resolutions = new ArrayList<>();
                for (UpdateResolutionDTO resolutionDTO : defectDTO.getResolutions()) {
                    Resolution resolution = new Resolution();
                    resolution.setDefect(defect);
                    resolution.setResolutiondate(resolutionDTO.getResolutiondate());
                    resolution.setResolution(resolutionDTO.getResolution());
                    resolutions.add(resolution);
                }
                defect.setResolutions(resolutions);
            }
           
            defectRepository.save(defect);
	            return "Defect updated successfully";
	        } else {
	            return "Defect not found";
	        }
	    }
    @Override
    public List<Defect> findDefectsByDeveloper(String developerId) {
    	List<Defect> defects = defectRepository.findByAssignedtodeveloperid(developerId);
        return defects;
    }
    
    @Override
    public Optional<Defect> findDefectById(Integer id) throws Exception {
    	Optional<Defect> defect = defectRepository.findById(id);
    	if(!defect.isPresent())
    	{
    		throw new Exception("Defect Not Found In The Database");
    	}
        return defect;
    }
    @Override
    public DefectReportDTO generateDefectReport(int projectId) throws Exception {
        
        	DefectReportDTO reportDTO = new DefectReportDTO();
            reportDTO.setProjectId(projectId);
     
            List<Defect> defects = defectRepository.findByProjectcode(projectId);
            if(defects.size()==0)
            {
            	throw new Exception("Records Not Found For This Project Id");
            }
            List<DefectDTO> defectDTOs = defects.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
     
            reportDTO.setDefects(defectDTOs);
            return reportDTO;
    }
    @Override
    public List<DefectDTO> getAllDefects() {
        List<Defect> defects = (List<Defect>) defectRepository.findAll();
        // Convert List<Defect> to List<DefectDTO> as needed
        List<DefectDTO> defectDTOs = defects.stream()
                .map(this::convertToDTO)
                .toList();
        return defectDTOs;
    }
    public DefectDTO convertToDTO(Defect defect) {
        DefectDTO defectDTO = new DefectDTO();
        defectDTO.setId(defect.getId());
        defectDTO.setTitle(defect.getTitle());
        defectDTO.setDefectdetails(defect.getDefectdetails());
        defectDTO.setStepstoreproduce(defect.getStepstoreproduce());
        defectDTO.setPriority(defect.getPriority());
        defectDTO.setDetectedon(defect.getDetectedon());
        defectDTO.setExpectedresolution(defect.getExpectedresolution());
        defectDTO.setReportedbytesterid(defect.getReportedbytesterid());
        defectDTO.setAssignedtodeveloperid(defect.getAssignedtodeveloperid());
        defectDTO.setSeverity(defect.getSeverity());
        defectDTO.setStatus(defect.getStatus());
        defectDTO.setProjectcode(defect.getProjectcode());
        List<ResolutionDTO> resolutionDTOs = defect.getResolutions().stream()
                .map(resolution -> {
                    ResolutionDTO resolutionDTO = new ResolutionDTO();
                    resolutionDTO.setId(resolution.getId());
                    resolutionDTO.setDefectId(resolution.getDefect().getId());
                    resolutionDTO.setResolutiondate(resolution.getResolutiondate());
                    resolutionDTO.setResolution(resolution.getResolution());
                    return resolutionDTO;
                })
                .collect(Collectors.toList());
        defectDTO.setResolutions(resolutionDTOs);
        return defectDTO;
    }
}


