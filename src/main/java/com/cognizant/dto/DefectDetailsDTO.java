package com.cognizant.dto;

import java.time.LocalDate;
import java.util.List;

import com.cognizant.entities.Resolution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefectDetailsDTO {

	private Integer id;
	private String title;
	private String defectdetails;
	private String stepstoreproduce;
	private String priority;
	private LocalDate detectedon;
	private LocalDate expectedresolution;
	private String reportedbytesterid;
	private String assignedtodeveloperid;
	private String severity;
	private String status;
	private int projectcode;
	private List<Resolution> resolutions;

}
