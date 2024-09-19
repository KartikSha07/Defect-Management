package com.cognizant.repository.test;
 
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
 
import com.cognizant.main.DefectsManagementApplication;
 
@SpringBootTest(classes=DefectsManagementApplication.class)
@ContextConfiguration(classes=DefectsManagementApplication.class)
class DefectsManagementApplicationTests {
 
	@Test
	void contextLoads() {
	}
 
}