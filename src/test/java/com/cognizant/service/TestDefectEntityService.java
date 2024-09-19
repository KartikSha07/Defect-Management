package com.cognizant.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import com.cognizant.dto.DefectDTO;
import com.cognizant.dto.DefectReportDTO;
import com.cognizant.dto.UpdateDefectDTO;
import com.cognizant.entities.Defect;
import com.cognizant.repositries.DefectEntityRepository;
import com.cognizant.services.BugResolutionCalculator;
import com.cognizant.services.DefectServiceImpl;
 
 
class TestDefectEntityService {
 
	    @Mock
	    private DefectEntityRepository defectRepository;
	    @Mock
	    private BugResolutionCalculator resolutionCalculator;
 
	    @InjectMocks
	    private DefectServiceImpl defectService;
 
	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.initMocks(this);
	    }
 
	    @Test
	     void testCreateDefect_Success() throws Exception {
	        DefectDTO defectDTO = new DefectDTO();
	        defectDTO.setTitle("Sample Defect");
	        defectDTO.setAssignedtodeveloperid("dev123");
	        defectDTO.setStepstoreproduce("abcdefghijk");
	        when(defectRepository.save(any(Defect.class))).thenReturn(new Defect());
	        when(resolutionCalculator.calculateExpectedResolutionDate(anyString()
	        		,anyString(),any(LocalDate.class))).thenReturn(LocalDate.of(2024,10,20));
	        String result = defectService.createDefect(defectDTO);
 
	        verify(defectRepository).save(any(Defect.class));
	        assertEquals("success", result);
	    }
	    @Test
	     void testUpdateDefect() {
	        UpdateDefectDTO updateDefectDTO = new UpdateDefectDTO(/* initialize with valid data */);
	        Defect defect=new Defect();
	        updateDefectDTO.setId(1);
	        when(defectRepository.findById(anyInt())).thenReturn(Optional.of(new Defect()));
	        when(defectRepository.save(defect)).thenReturn(defect);
	        defectService.updateDefect(updateDefectDTO);
	        verify(defectRepository).findById(anyInt());
	        verify(defectRepository).save(any(Defect.class));
	    }
 
	    
	    @Test
	     void testFindDefectById() throws Exception {
	        int defectId = 1;
	        when(defectRepository.findById(defectId)).thenReturn(Optional.of(new Defect()));
	        Optional<Defect> result = defectService.findDefectById(defectId);
	        verify(defectRepository).findById(defectId);
	    }
	    @Test
	    public void testGenerateDefectReportWithNonEmptyDefectList() throws Exception {
	        int projectId = 456;
	        List<Defect> defects = new ArrayList<>();
	        defects.add(new Defect());
	        defects.add(new Defect());
	        when(defectRepository.findByProjectcode(projectId)).thenReturn(defects);
 
	        DefectReportDTO reportDTO = defectService.generateDefectReport(projectId);
 
	        assertEquals(projectId, reportDTO.getProjectId());
	        assertEquals(defects.size(), reportDTO.getDefects().size());
	    }
	    @Test
	    public void testConvertToDTO() {
	        Defect defect = new Defect(/* initialize with relevant properties */);
	        DefectDTO defectDTO = defectService.convertToDTO(defect);
 
	    }
	    @Test
	    void testCreateDefect_MaximumBugsPerDayExceeded() throws Exception {
	        String developerId = "dev123";
	        int maxBugsPerDay = 5;
	        // Mock behavior to return maximum bugs per day
	        when(defectRepository.save(any(Defect.class))).thenReturn(new Defect());
	        // Create defectDTOs with developer having maximum bugs assigned for the day
	        for (int i = 0; i < maxBugsPerDay; i++) {
	            DefectDTO defectDTO = new DefectDTO();
	            defectDTO.setAssignedtodeveloperid(developerId);
	            defectDTO.setStepstoreproduce("Test step"); // Set a non-null value for stepstoreproduce
	            // Add other necessary fields
	            defectService.createDefect(defectDTO);
	        }
	        // Call createDefect method one more time
	        DefectDTO defectDTO = new DefectDTO();
	        defectDTO.setAssignedtodeveloperid(developerId);
	        defectDTO.setStepstoreproduce("Test step"); // Set a non-null value for stepstoreproduce
	        // Add other necessary fields
	        String result = defectService.createDefect(defectDTO);
	        // Assert that the result is "Developer has reached the maximum bug assignment limit for today"
	        assertEquals("Developer has reached the maximum bug assignment limit for today", result);
	    }
	    @Test
	    void testGetAllDefects_Success() {
 
	            List<Defect> defects = new ArrayList<>();
 
	            // Add defects to the list
	            when(defectRepository.findAll()).thenReturn(defects);
	            List<DefectDTO> result = defectService.getAllDefects();
	            assertEquals(defects.size(), result.size());
 
	        }
	    @Test
	    void testFindDefectsByDeveloperId_Success() {
	        String developerId = "dev123";
	        // Mock behavior to return an empty list of defects for the given developerId
	        when(defectRepository.findByAssignedtodeveloperid(developerId)).thenReturn(new ArrayList<>());
	        // Assert that the method throws an exception with the expected message
	        Exception exception = assertThrows(Exception.class, () -> defectService.findDefectsByDeveloper(developerId));
	        assertEquals("DeveloperId is invalid", exception.getMessage());
	    }
	    @Test
	    void testCreateDefect_EmptyTitle() {
	    	DefectDTO defectDTO = new DefectDTO();
	        defectDTO.setTitle("");
	        // Assertion using try-catch block
	        try {
	        	defectService.createDefect(defectDTO);
	        	Assertions.fail("Expected an IllegalArgumentException to be thrown");
	        	} 
	        catch (Exception e) {
	        	// Verify if the exception message matches the expected message
	        	assertEquals("Stepstoreproduce is required", e.getMessage());
 
	        }

 
	    }
	    @Test
	    void testCreateDefect_NullTitle() {
	        DefectDTO defectDTO = new DefectDTO();
	        defectDTO.setTitle(null);
	     // Assertion using assertThrows
	        Exception exception = assertThrows(Exception.class, () -> defectService.createDefect(defectDTO));
	        assertEquals("Stepstoreproduce is required", exception.getMessage());
	    }
	    
	    @Test
	    void testCreateDefect_NullStepToReproduce() {
	        // Test case : Null step to reproduce
	        DefectDTO defectDTO = new DefectDTO();
	        defectDTO.setTitle("Sample Defect");
	        defectDTO.setAssignedtodeveloperid("dev123");
	        defectDTO.setStepstoreproduce(null);
	        Exception exception1 = assertThrows(Exception.class, () -> defectService.createDefect(defectDTO));
	        assertEquals("Stepstoreproduce is required", exception1.getMessage());
	    }
	    @Test
	    void testUpdateDefect_DefectNotFound() {
	        // Test case : Defect not found during update
	        UpdateDefectDTO updateDefectDTO = new UpdateDefectDTO();
	        updateDefectDTO.setId(1);
	        when(defectRepository.findById(anyInt())).thenReturn(Optional.empty());
	        String result = defectService.updateDefect(updateDefectDTO);
	        assertEquals("Defect not found", result);
	    }
	    @Test
	    void testUpdateDefect_WithResolutions() {
	        // Test case : Update defect with resolutions
	        UpdateDefectDTO updateDefectDTO = new UpdateDefectDTO();
	        updateDefectDTO.setId(1);
	        updateDefectDTO.setStatus("Resolved");
	        updateDefectDTO.setResolutions(new ArrayList<>());
	        when(defectRepository.findById(anyInt())).thenReturn(Optional.of(new Defect()));
	        String result = defectService.updateDefect(updateDefectDTO);
	        assertEquals("Defect updated successfully", result);
	    }
	    @Test
	    void testFindDefectById_DefectNotFound() throws Exception {
	        // Test case : Defect not found by ID
	        int defectId = 1;
	        when(defectRepository.findById(defectId)).thenReturn(Optional.empty());
	        Exception exception = assertThrows(Exception.class, () -> defectService.findDefectById(defectId));
	        assertEquals("Defect Not Found In The Database", exception.getMessage());
	    }
	    @Test
	    void testGenerateDefectReportWithEmptyDefectList() throws Exception {
	        // Test case : Empty defect list for report generation
	        int projectId = 456;
	        when(defectRepository.findByProjectcode(projectId)).thenReturn(new ArrayList<>());
	        Exception exception = assertThrows(Exception.class, () -> defectService.generateDefectReport(projectId));
	        assertEquals("Records Not Found For This Project Id", exception.getMessage());
	    }
 
	   }