package com.cognizant.controller;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.dto.DefectDTO;
import com.cognizant.dto.DefectDetailsDTO;
import com.cognizant.dto.DefectReportDTO;
import com.cognizant.dto.UpdateDefectDTO;
import com.cognizant.entities.Defect;
import com.cognizant.services.DefectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
@Tag(name="Defects Management",description="Rest API of Defect management module")
public class DefectController {

	private final Logger logger = LoggerFactory.getLogger(DefectController.class);

	
    private DefectService defectService;
	
	DefectController(DefectService defectService)
	{
		this.defectService =  defectService;
	}

	@Operation(description="Create a new defect")
	@PostMapping("defects/new")
	public ResponseEntity<?> createDefect(@Valid @RequestBody DefectDTO defectDTO) throws Exception {
		logger.info("Received request to create defect with title: {}", defectDTO.getTitle());
		String result = defectService.createDefect(defectDTO);
	    if (result.equals("success")) {
	        return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\": \"Success\"}");
	    } else if (result.equals("Developer has reached the maximum bug assignment limit for today")) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"error\": \"Maximum bug assignment limit reached for today.\"}");
	    }else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + result + "\"}");

	}
	@Operation(description="update the status and provide resolution to defect")
    @PutMapping("/defects/resolve")
    public ResponseEntity<String> resolveDefect(@RequestBody UpdateDefectDTO defectDTO) throws Exception {
        logger.info("Received request to resolve defect with ID: {}", defectDTO.getId());
        String status = defectService.updateDefect(defectDTO);
        if (status.equals("Defect updated successfully")) {
            return new ResponseEntity<>("{\"message\": \"Success\"}", HttpStatus.OK);
        } else if(status.equals("Defect not found")) {
        	throw new Exception("Defect Not found Exception");
        }else  return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);

    }
	@Operation(description="Get defects based on DeveloperId")
    @GetMapping("/defects/assignedto/{developerId}")
    public ResponseEntity<List<Defect>> getDefectsByDeveloper(@PathVariable String developerId) throws Exception {
		logger.info("Received request to get defects assigned to developer with developerID: {}", developerId);
        List<Defect> defects = defectService.findDefectsByDeveloper(developerId);
        if (defects != null && !defects.isEmpty()) {
            return ResponseEntity.ok(defects);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }
    
	@Operation(description="Get defects based on Id")
    @GetMapping("/defects/{defectId}")
    public ResponseEntity<DefectDetailsDTO> getDefectDetails(@PathVariable Integer defectId) throws RuntimeException, Exception {
		logger.info("Received request to get details of defect with ID: {}", defectId);
    	Defect defect = defectService.findDefectById(defectId).orElseThrow(() -> new RuntimeException("Defect not found with id " + defectId));
        DefectDetailsDTO defectDetailsDTO = new DefectDetailsDTO();
        defectDetailsDTO.setId(defect.getId());
        defectDetailsDTO.setTitle(defect.getTitle());
        defectDetailsDTO.setDefectdetails(defect.getDefectdetails());
        defectDetailsDTO.setStepstoreproduce(defect.getStepstoreproduce());
        defectDetailsDTO.setPriority(defect.getPriority());
        defectDetailsDTO.setDetectedon(defect.getDetectedon());
        defectDetailsDTO.setExpectedresolution(defect.getExpectedresolution());
        defectDetailsDTO.setReportedbytesterid(defect.getReportedbytesterid());
        defectDetailsDTO.setAssignedtodeveloperid(defect.getAssignedtodeveloperid());
        defectDetailsDTO.setSeverity(defect.getSeverity());
        defectDetailsDTO.setStatus(defect.getStatus());
        defectDetailsDTO.setProjectcode(defect.getProjectcode());
        defectDetailsDTO.setResolutions(defect.getResolutions());

        return ResponseEntity.ok(defectDetailsDTO);
    }
    
	@Operation(description="Generate Report based on project code")
    @GetMapping("/defects/report/{projectId}")
    public ResponseEntity<DefectReportDTO> getDefectReport(@PathVariable int projectId) throws Exception {
		logger.info("Received request to generate defect report for project with ID: {}", projectId);
    	DefectReportDTO defectReportDTO = defectService.generateDefectReport(projectId);
        return new ResponseEntity<>(defectReportDTO, HttpStatus.OK);
    }
	@Operation(description="Get All Defects")
    @GetMapping("/defects/getAll")
    public ResponseEntity<List<DefectDTO>> getAllDefects() {
        logger.info("Received request to get all defects");
        List<DefectDTO> defects = defectService.getAllDefects();
        return new ResponseEntity<>(defects, HttpStatus.OK);
    }

}


