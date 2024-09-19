package com.cognizant.Defects.controller;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
import com.cognizant.controller.DefectController;
import com.cognizant.dto.DefectDTO;
import com.cognizant.dto.DefectDetailsDTO;
import com.cognizant.dto.DefectReportDTO;
import com.cognizant.dto.UpdateDefectDTO;
import com.cognizant.entities.Defect;
import com.cognizant.services.DefectService;
 
public class DefectControllerTest {
 
	@Mock
	private DefectService defectService;
 
	@InjectMocks
	private DefectController defectController;
 
	@SuppressWarnings("deprecation")
 
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
 
	@Test
	public void testCreateDefect_Success() throws Exception {
		// Mocking
		DefectDTO defectDTO = new DefectDTO();
		Mockito.when(defectService.createDefect(Mockito.any())).thenReturn("success");
		// Execution
		ResponseEntity<?> responseEntity = defectController.createDefect(defectDTO);
		// Assertion
		 assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		    assertEquals("{\"message\": \"Success\"}", responseEntity.getBody());
	}
 
	@Test
	public void testCreateDefect_Failure() throws Exception {
		// Mocking
		DefectDTO defectDTO = new DefectDTO();
		Mockito.when(defectService.createDefect(Mockito.any())).thenReturn("Failure");
		// Execution
		ResponseEntity<?> responseEntity = defectController.createDefect(defectDTO);
		// Assertion
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("{\"error\": \"Failure\"}", responseEntity.getBody());
	}
 
	@Test
	void testResolveDefect_Success() throws Exception {
		// Given
		UpdateDefectDTO defectDTO = new UpdateDefectDTO();
		when(defectService.updateDefect(defectDTO)).thenReturn("Defect updated successfully");
 
		// When
		ResponseEntity<String> response = defectController.resolveDefect(defectDTO);
 
		// Then
		assertEquals("{\"message\": \"Success\"}", response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
 
	@Test
	void testResolveDefect_NotFound() throws Exception {
		// Given
		UpdateDefectDTO defectDTO = new UpdateDefectDTO();
		when(defectService.updateDefect(defectDTO)).thenReturn("Defect not found");
 
		// When & Then
		Exception exception = assertThrows(Exception.class, () -> {
			defectController.resolveDefect(defectDTO);
		});
 
		assertEquals("Defect Not found Exception", exception.getMessage());
	}
 
	@Test
	public void testUpdateDefect_Success() throws Exception {
		// Mocking
		DefectDTO defectDTO = new DefectDTO();
		Mockito.when(defectService.createDefect(defectDTO)).thenReturn("success");
 
		// Execution
		ResponseEntity<?> responseEntity = defectController.createDefect(defectDTO);
 
		// Assertion
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("{\"message\": \"Success\"}", responseEntity.getBody());
	}
 
	@Test
	void testGetDefectsByDeveloper_Success() throws Exception {
		// Given
		String developerId = "developer123";
		List<Defect> expectedDefects = new ArrayList<>();
		expectedDefects.add(new Defect());
		expectedDefects.add(new Defect());
		Mockito.when(defectService.findDefectsByDeveloper(developerId)).thenReturn(expectedDefects);
 
		// When
		ResponseEntity<List<Defect>> response = defectController.getDefectsByDeveloper(developerId);
 
		// Then
		assertEquals(expectedDefects, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
 
//    @Test
    void testGetDefectDetails_Success() throws Exception {
        // Given
        Integer defectId = 1;
        Defect defect = new Defect();
        defect.setId(defectId);
        defect.setTitle("Title");
        // Set other properties as needed
        DefectDetailsDTO expectedDto = new DefectDetailsDTO();
        expectedDto.setId(defect.getId());
        expectedDto.setTitle(defect.getTitle());
        // Set other properties as needed
        Mockito.when(defectService.findDefectById(defectId)).thenReturn(Optional.of(defect));
        // When
        ResponseEntity<DefectDetailsDTO> response = defectController.getDefectDetails(defectId);
        // Then
        assertEquals(expectedDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    void testGetDefectDetails_DefectNotFound() throws Exception {
        // Given
        Integer defectId = 1;
        Mockito.when(defectService.findDefectById(defectId)).thenReturn(Optional.empty());
        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            defectController.getDefectDetails(defectId);
        });
        assertEquals("Defect not found with id " + defectId, exception.getMessage());
    }
	@Test
	void testGetDefectReport_Success() throws Exception {
		// Given
		int projectId = 123;
		DefectReportDTO expectedReportDTO = new DefectReportDTO();
		// Set up expected report DTO as needed
 
		Mockito.when(defectService.generateDefectReport(projectId)).thenReturn(expectedReportDTO);
 
		// When
		ResponseEntity<DefectReportDTO> response = defectController.getDefectReport(projectId);
 
		// Then
		assertEquals(expectedReportDTO, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
 
	@Test
	void testGetAllDefects_Success() {
		// Given
		List<DefectDTO> expectedDefects = new ArrayList<>();
		expectedDefects.add(new DefectDTO());
		expectedDefects.add(new DefectDTO());
		Mockito.when(defectService.getAllDefects()).thenReturn(expectedDefects);
 
		// When
		ResponseEntity<List<DefectDTO>> response = defectController.getAllDefects();
 
		// Then
		assertEquals(expectedDefects, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}