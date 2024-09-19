package com.cognizant.repository.test;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
 
@DataJpaTest
@ContextConfiguration(classes = DefectsManagementApplication.class)
public class TestDefectEntityRepository {
 
	@Autowired private DefectEntityRepository defectRepository;         
	@Autowired private ResolutionsEntityRepository resolutionRepository;   
	
	@Test
	public void testSaveAndRetrieveDefect()
	{         
		Defect sampleDefect = new Defect();         
		sampleDefect.setTitle("Sample Defect");         
		sampleDefect.setDefectdetails("Sample defect details");        
		sampleDefect.setPriority("P1");         
		sampleDefect.setDetectedon(LocalDate.now());        
		defectRepository.save(sampleDefect);         
		Iterable<Defect> savedDefectsIterable = defectRepository.findAll();         
		List<Defect> defectList = new ArrayList<>();         
		savedDefectsIterable.forEach(defectList::add);         
		assertThat(defectList).isNotEmpty();         
		assertEquals("Sample Defect", defectList.get(0).getTitle());     
	}
 
	@Test
	public void testFindAllPositive()
	{         
		Defect defect1 = new Defect();         
		defect1.setTitle("Defect 1");         
		defect1.setPriority("P1");         
		defect1.setDetectedon(LocalDate.now());       
		defectRepository.save(defect1);         
		Defect defect2 = new Defect();         
		defect2.setTitle("Defect 2");         
		defect2.setPriority("P2");         
		defect2.setDetectedon(LocalDate.now());          
		defectRepository.save(defect2);         
		Iterable<Defect> allDefects = defectRepository.findAll();         
		assertThat(allDefects).isNotEmpty();    
	 }
    @Test
    public void testFindAllNegative()
    {       
    	Iterable<Defect> it=defectRepository.findAll();
		assertTrue(!it.iterator().hasNext());
    	   
    }     
    @Test
    public void testFindByIdPositive()
    {     
    	Defect defect = new Defect();         
    	defect.setTitle("Test Defect");         
    	defect.setPriority("P1");     
    	defect.setDetectedon(LocalDate.now());
    	Defect savedDefect = defectRepository.save(defect);        
    	Defect retrievedDefect = defectRepository.findById(savedDefect.getId()).orElse(null);         	
    	assertThat(retrievedDefect).isNotNull();         
    	assertThat(retrievedDefect).isEqualTo(savedDefect);     
    }     
    @Test
    public void testFindByIdNegative()
    {         
    	Defect retrievedDefect = defectRepository.findById((int) -1L).orElse(null);         	
    	assertThat(retrievedDefect).isNull();     
    }     
    @Test
    public void testSavePositive()
    {     
    	Defect defect = new Defect();         
    	defect.setTitle("Test Defect");         
    	defect.setPriority("P1");         
    	defect.setDetectedon(LocalDate.now());
    	Defect savedDefect = defectRepository.save(defect);         	
    	assertThat(savedDefect).isNotNull();         
    	assertThat(savedDefect.getId()).isNotNull();     
    }     
 
    @Test
    public void testDeletePositive()
    {         
    	Defect defect = new Defect();         
    	defect.setTitle("Test Defect");         
    	defect.setPriority("P1");   
    	defect.setDetectedon(LocalDate.now());
    	Defect savedDefect = defectRepository.save(defect);         	
    	defectRepository.deleteById(savedDefect.getId());         
    	Defect deletedDefect = defectRepository.findById(savedDefect.getId()).orElse(null);         	
    	assertThat(deletedDefect).isNull();     
    }     
    @Test
    public void testDeleteNegative()
    {         
    	Optional<Defect> ut= defectRepository.findById(1);
		assertTrue(!ut.isPresent());    
	}
    
    @Test
    public void testAddResolutionToDefect()
    {         
    Defect defect = new Defect();         
    defect.setTitle("Defect with Resolution");         
    defect.setPriority("P1");         
    defect.setDetectedon(LocalDate.now());       
    Resolution resolution = new Resolution();         
    resolution.setResolution("Sample resolution");
    resolution.setResolutiondate(LocalDate.now());   
    resolution.setDefect(defect);         
    defect.getResolutions().add(resolution);         
    defectRepository.save(defect);         
    resolutionRepository.save(resolution);         
    Defect savedDefect = defectRepository.findById(defect.getId()).orElse(null);         
    assertThat(savedDefect).isNotNull();         
    assertThat(savedDefect.getResolutions()).isNotEmpty();     
    }
 
 
}