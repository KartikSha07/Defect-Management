package com.cognizant.repository.test;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
 
import com.cognizant.entities.Defect;
import com.cognizant.entities.Resolution;
import com.cognizant.main.DefectsManagementApplication;
import com.cognizant.repositries.DefectEntityRepository;
import com.cognizant.repositries.ResolutionsEntityRepository;
 
 
import jakarta.validation.ConstraintViolationException;
 
@DataJpaTest
@ContextConfiguration(classes = DefectsManagementApplication.class)
public class TestResolutionsEntityRepository {
 
	@Autowired
    private ResolutionsEntityRepository resolutionRepository;
    @Autowired
    private DefectEntityRepository defectRepository;

    @Test
    public void testSaveAndRetrieveResolution() {        
    	Defect defect = new Defect();
        defect.setTitle("Sample Defect");
        defect.setPriority("P1");
        defect.setDetectedon(LocalDate.now()); 
        defectRepository.save(defect);
        Resolution resolution = new Resolution();
        resolution.setResolution("Sample resolution");
        resolution.setResolutiondate(LocalDate.now());         
        resolution.setDefect(defect);
        resolutionRepository.save(resolution);
        Iterable<Resolution> savedResolutionsIterable = resolutionRepository.findAll();
        List<Resolution> resolutionList = new ArrayList<>();
        savedResolutionsIterable.forEach(resolutionList::add);
        assertThat(resolutionList).isNotEmpty();
        assertEquals("Sample resolution", resolutionList.get(0).getResolution());
    }
    @Test
    public void testFindAllPositive() {
        Defect defect = new Defect();
        defect.setTitle("Defect with Resolution");
        defect.setPriority("P1");
        defect.setDetectedon(LocalDate.now()); 
        defectRepository.save(defect);
        Resolution resolution1 = new Resolution();
        resolution1.setResolution("Resolution 1");
        resolution1.setResolutiondate(LocalDate.now());   
        resolution1.setDefect(defect);
        resolutionRepository.save(resolution1);
        Resolution resolution2 = new Resolution();
        resolution2.setResolution("Resolution 2");
        resolution2.setResolutiondate(LocalDate.now());    
        resolution2.setDefect(defect);
        resolutionRepository.save(resolution2);
        Iterable<Resolution> allResolutions = resolutionRepository.findAll();
        assertThat(allResolutions).isNotEmpty();
    }
    @Test
    public void testFindAllNegative() {
        Iterable<Resolution> resolutions = resolutionRepository.findAll();
        assertFalse(resolutions.iterator().hasNext());
    }
    @Test
    public void testFindByIdPositive() {
        Resolution resolution = new Resolution();
        resolution.setResolution("Test Resolution");
        resolution.setResolutiondate(LocalDate.now());    
        Resolution savedResolution = resolutionRepository.save(resolution);
        Optional<Resolution> retrievedResolutionOptional = resolutionRepository.findById(savedResolution.getId());
        assertTrue(retrievedResolutionOptional.isPresent());
        Resolution retrievedResolution = retrievedResolutionOptional.get();
        assertEquals("Test Resolution", retrievedResolution.getResolution());
    }
    @Test
    public void testFindByIdNegative() {
        Optional<Resolution> retrievedResolutionOptional = resolutionRepository.findById(-1);
        assertFalse(retrievedResolutionOptional.isPresent());
    }
    @Test
    public void testSavePositive() {
        Resolution resolution = new Resolution();
        resolution.setResolution("Test Resolution");
        resolution.setResolutiondate(LocalDate.now());    
        Resolution savedResolution = resolutionRepository.save(resolution);
        assertThat(savedResolution).isNotNull();
        assertThat(savedResolution.getId()).isNotNull();
    }
    @Test
    public void testSaveNegative() {
        Resolution invalidResolution = new Resolution();
        invalidResolution.setResolution("Invalid Resolution");
        invalidResolution.setResolutiondate(null); 
        assertThatThrownBy(() -> resolutionRepository.save(invalidResolution))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("must not be null");
    }
 
 
    @Test
    public void testDeletePositive() {
        Resolution resolution = new Resolution();
        resolution.setResolution("Test Resolution");
        resolution.setResolutiondate(LocalDate.now());   
        Resolution savedResolution = resolutionRepository.save(resolution);
        resolutionRepository.deleteById(savedResolution.getId());
        Optional<Resolution> deletedResolutionOptional = resolutionRepository.findById(savedResolution.getId());
        assertFalse(deletedResolutionOptional.isPresent());
    }
    @Test
    public void testDeleteNegative() {
        Optional<Resolution> resolution = resolutionRepository.findById(1);
        assertFalse(resolution.isPresent());
    }
    @Test
    public void testFindAllResolutions() {
        Resolution resolution1 = new Resolution();
        resolution1.setResolution("Resolution 1");
        resolution1.setResolutiondate(LocalDate.now()); 
        resolutionRepository.save(resolution1);
        Resolution resolution2 = new Resolution();
        resolution2.setResolution("Resolution 2");
        resolution2.setResolutiondate(LocalDate.now());  
        resolutionRepository.save(resolution2);
        Iterable<Resolution> allResolutions = resolutionRepository.findAll();
        assertThat(allResolutions).isNotEmpty();
    }
    @Test
    public void testDeleteResolution() {
        Resolution resolution = new Resolution();
        resolution.setResolution("Test Resolution");
        resolution.setResolutiondate(LocalDate.now());  
        Resolution savedResolution = resolutionRepository.save(resolution);
        resolutionRepository.deleteById(savedResolution.getId());
        Optional<Resolution> deletedResolution = resolutionRepository.findById(savedResolution.getId());
        assertTrue(deletedResolution.isEmpty());
    }
    @Test
    public void testFindByDefect() {
        Defect defect = new Defect();
        defect.setTitle("Test Defect");
        defect.setDetectedon(LocalDate.now());
        Defect savedDefect = defectRepository.save(defect);
        Resolution resolution = new Resolution();
        resolution.setResolution("Resolution for Test Defect");
        resolution.setResolutiondate(LocalDate.now());  
        resolution.setDefect(savedDefect);
        resolutionRepository.save(resolution);
        List<Resolution> resolutionsForDefect = resolutionRepository.findByDefect(savedDefect);
        assertThat(resolutionsForDefect).isNotEmpty();
        assertEquals("Resolution for Test Defect", resolutionsForDefect.get(0).getResolution());
    }
}